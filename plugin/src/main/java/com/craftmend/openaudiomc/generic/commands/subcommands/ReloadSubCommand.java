package com.craftmend.openaudiomc.generic.commands.subcommands;

import com.craftmend.openaudiomc.api.EventApi;
import com.craftmend.openaudiomc.api.events.client.SystemReloadEvent;
import com.craftmend.openaudiomc.generic.commands.interfaces.SubCommand;
import com.craftmend.openaudiomc.generic.commands.objects.Argument;
import com.craftmend.openaudiomc.generic.oac.OpenaudioAccountService;
import com.craftmend.openaudiomc.generic.client.objects.ClientConnection;
import com.craftmend.openaudiomc.generic.networking.interfaces.NetworkingService;
import com.craftmend.openaudiomc.generic.platform.Platform;
import com.craftmend.openaudiomc.generic.platform.interfaces.TaskService;
import com.craftmend.openaudiomc.generic.user.User;
import com.openaudiofabric.OpenAudioFabric;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand() {
        super("reload", "restart");
        registerArguments(new Argument("", "Reloads the config.yml and connection system"));
    }

    @Override
    public void onExecute(User sender, String[] args) {
        message(sender, Platform.makeColor("RED") + "Reloading OpenAudioMc data (config and account details)...");
        OpenAudioFabric.getInstance().getConfiguration().reloadConfig();
        OpenAudioFabric.getService(OpenaudioAccountService.class).syncAccount();

        message(sender, Platform.makeColor("RED") + "Shutting down network service and logging out...");

        for (ClientConnection client : OpenAudioFabric.getService(NetworkingService.class).getClients()) {
            client.kick(() -> {});
        }

        OpenAudioFabric.getService(NetworkingService.class).stop();

        message(sender, Platform.makeColor("RED") + "Re-activating account...");
        OpenAudioFabric.resolveDependency(TaskService.class).runAsync(() -> OpenAudioFabric.getService(NetworkingService.class).connectIfDown());

        EventApi.getInstance().callEvent(new SystemReloadEvent());

        message(sender, Platform.makeColor("GREEN") + "Reloaded system! Welcome back.");
    }
}
