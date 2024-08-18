package com.craftmend.openaudiomc.generic.state.collectors;

import com.craftmend.openaudiomc.generic.client.objects.ClientConnection;
import com.craftmend.openaudiomc.generic.networking.interfaces.NetworkingService;
import com.craftmend.openaudiomc.generic.state.interfaces.StateDetail;
import com.openaudiofabric.OpenAudioFabric;

public class GeneralConnectedClients implements StateDetail {
    @Override
    public String title() {
        return "Clients";
    }

    @Override
    public String value() {
        int clients = 0;
        for (ClientConnection clientConnection : OpenAudioFabric.getService(NetworkingService.class).getClients()) {
            if (clientConnection.isConnected()) clients++;
        }
        return clients + "";
    }
}
