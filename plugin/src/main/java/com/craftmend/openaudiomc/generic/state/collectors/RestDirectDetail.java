package com.craftmend.openaudiomc.generic.state.collectors;

import com.craftmend.openaudiomc.generic.platform.OaColor;
import com.craftmend.openaudiomc.generic.rd.RestDirectService;
import com.craftmend.openaudiomc.generic.state.interfaces.StateDetail;
import com.openaudiofabric.OpenAudioFabric;

public class RestDirectDetail implements StateDetail {
    @Override
    public String title() {
        return "RestDirect";
    }

    @Override
    public String value() {
        if (OpenAudioFabric.getService(RestDirectService.class).isRunning()) {
            return OaColor.GREEN + "ONLINE!";
        } else {
            return OaColor.RED + "NOT COMPATIBLE!";
        }
    }
}
