package com.craftmend.openaudiomc.spigot.modules.commands.subcommands.region;

import com.craftmend.openaudiomc.generic.commands.interfaces.SubCommand;
import com.craftmend.openaudiomc.generic.database.DatabaseService;
import com.craftmend.openaudiomc.generic.user.User;
import com.craftmend.openaudiomc.spigot.OpenAudioMcSpigot;
import com.craftmend.openaudiomc.spigot.modules.regions.objects.RegionProperties;
import com.craftmend.openaudiomc.spigot.modules.regions.objects.TimedRegionProperties;
import com.craftmend.openaudiomc.spigot.modules.regions.registry.WorldRegionManager;
import com.openaudiofabric.OpenAudioFabric;

import org.bukkit.ChatColor;

public class RegionDeleteSubCommand extends SubCommand {

    private final OpenAudioMcSpigot openAudioMcSpigot;

    public RegionDeleteSubCommand(OpenAudioMcSpigot openAudioMcSpigot) {
        super("delete");
        this.openAudioMcSpigot = openAudioMcSpigot;
    }

    @Override
    public void onExecute(User sender, String[] args) {
        String targetRegion = args[1].toLowerCase();

        WorldRegionManager worldRegionManager = openAudioMcSpigot.getRegionModule().getWorld(sender.getWorld());

        RegionProperties legacyMatchedRegion = openAudioMcSpigot.getRegionModule().getRegionsWithoutWorld()
                .stream()
                .filter(regionProperties -> regionProperties.getRegionName().equalsIgnoreCase(targetRegion))
                .findFirst().orElseGet(() -> null);

        if (legacyMatchedRegion != null) {
            OpenAudioFabric.getService(DatabaseService.class).getRepository(RegionProperties.class)
                    .delete(legacyMatchedRegion);
            message(sender, ChatColor.RED + "The legacy region " + targetRegion + " no longer has a sound linked to it.");
            openAudioMcSpigot.getRegionModule().getRegionsWithoutWorld().remove(legacyMatchedRegion);
        }

        // check if it was valid in the first place
        RegionProperties rp = worldRegionManager.getRegionProperties(targetRegion);
        if (rp != null) {
            if (rp.getId() != null && !(rp instanceof TimedRegionProperties)) {
                OpenAudioFabric.getService(DatabaseService.class).getRepository(RegionProperties.class)
                        .delete(rp);
            }

            if (rp instanceof TimedRegionProperties) {
                ((TimedRegionProperties) rp).destroy();
            }

            worldRegionManager.unregisterRegion(targetRegion);

            message(sender, ChatColor.RED + "The WorldGuard region with the id " + targetRegion + " no longer has a sound linked to it.");
        } else {
            message(sender, ChatColor.RED + "There's no worldguard region by the name " + targetRegion);
        }
        openAudioMcSpigot.getRegionModule().forceUpdateRegions();
    }
}
