package com.craftmend.openaudiomc.spigot.modules.speakers.listeners;

import com.craftmend.openaudiomc.OpenAudioMc;
import com.craftmend.openaudiomc.generic.database.DatabaseService;
import com.craftmend.openaudiomc.generic.environment.MagicValue;
import com.craftmend.openaudiomc.spigot.modules.speakers.SpeakerService;
import com.craftmend.openaudiomc.spigot.modules.speakers.objects.MappedLocation;
import com.craftmend.openaudiomc.spigot.modules.speakers.objects.Speaker;
import com.craftmend.openaudiomc.spigot.modules.speakers.utils.SpeakerUtils;
import lombok.AllArgsConstructor;
import net.minecraft.entity.player.PlayerEntity;

@AllArgsConstructor
public class SpeakerDestroyListener implements Listener {

    private OpenAudioMc openAudioMc;
    private SpeakerService speakerService;

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        for (Block broken : event.blockList()) {
            if (SpeakerUtils.isSpeakerSkull(broken)) {
                MappedLocation location = new MappedLocation(broken.getLocation());
                Speaker speaker = speakerService.getSpeaker(location);
                if (speaker != null) {
                    broken.getWorld().dropItem(
                            broken.getLocation(),
                            SpeakerUtils.getSkull(speaker.getSource(), speaker.getRadius())
                    );
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block broken = event.getBlock();
        if (SpeakerUtils.isSpeakerSkull(broken)) {
            if (!isAllowed(event.getPlayer())) {
                event.getPlayer().sendMessage(MagicValue.COMMAND_PREFIX.get(String.class) + "You are not allowed to break OpenAudioMc speakers, please ask the server administrator for more information.");
                event.setCancelled(true);
                return;
            }

            MappedLocation location = new MappedLocation(broken.getLocation());
            Speaker speaker = speakerService.getSpeaker(location);
            if (speaker == null) return;

            speakerService.unlistSpeaker(location);

            //save to config
            OpenAudioMc.getService(DatabaseService.class).getRepository(Speaker.class).delete(speaker);

            event.getPlayer().sendMessage(MagicValue.COMMAND_PREFIX.get(String.class) + ChatColor.RED + "Speaker destroyed");

            event.getBlock().getWorld().dropItem(
                    event.getBlock().getLocation(),
                    SpeakerUtils.getSkull(speaker.getSource(), speaker.getRadius())
            );

            try {
                event.setDropItems(false);
            } catch (Exception ignored) {}
        }
    }

    private boolean isAllowed(PlayerEntity player) {
        return player.hasPermissionLevel(2);
        //        || player.hasPermission("openaudiomc.speakers.*")
        //        || player.hasPermission("openaudiomc.*")
        //        || player.hasPermission("openaudiomc.speakers.destroy");
    }

}
