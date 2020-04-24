package com.craftmend.openaudiomc.bungee.modules.commands.commands;

import com.craftmend.openaudiomc.OpenAudioMc;
import com.craftmend.openaudiomc.bungee.modules.player.objects.BungeePlayerSelector;

import com.craftmend.openaudiomc.generic.commands.adapters.BungeeCommandSenderAdapter;
import com.craftmend.openaudiomc.generic.commands.helpers.CommandMiddewareExecutor;
import com.craftmend.openaudiomc.generic.commands.interfaces.CommandMiddleware;
import com.craftmend.openaudiomc.generic.commands.middleware.CatchCrashMiddleware;
import com.craftmend.openaudiomc.generic.commands.middleware.CleanStateCheckMiddleware;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BungeeAudioCommand extends Command {

    private CommandMiddleware[] commandMiddleware = new CommandMiddleware[] {
            new CatchCrashMiddleware(),
            new CleanStateCheckMiddleware()
    };

    public BungeeAudioCommand() {
        super("audio", null, "sound", "connect", "muziek", "mcaudio", "mcconnect", "mconnect", "geluid");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (CommandMiddewareExecutor.shouldBeCanceled(new BungeeCommandSenderAdapter(sender), null, commandMiddleware)) return;

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            OpenAudioMc.getInstance().getNetworkingService().getClient(player.getUniqueId()).publishUrl();
        } else {
            if (args.length == 0) {
                sender.sendMessage(OpenAudioMc.getInstance().getCommandModule().getCommandPrefix() + "You must provide a player name OR selector to send trigger the URL");
                return;
            }

            for (ProxiedPlayer player : new BungeePlayerSelector(args[0]).getPlayers(sender)) {
                OpenAudioMc.getInstance().getNetworkingService().getClient(player.getUniqueId()).publishUrl();
            }
        }
    }
}
