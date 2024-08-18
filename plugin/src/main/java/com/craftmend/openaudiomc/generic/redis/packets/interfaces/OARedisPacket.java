package com.craftmend.openaudiomc.generic.redis.packets.interfaces;

import com.craftmend.openaudiomc.generic.redis.RedisService;
import com.openaudiofabric.OpenAudioFabric;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public abstract class OARedisPacket {

    public abstract String serialize();

    public OARedisPacket deSerialize(String json) {
        return OpenAudioFabric.getGson().fromJson(json, getClass());
    }

    public void receive(OARedisPacket received) {
        if (OpenAudioFabric.getService(RedisService.class).getServiceId().equals(getSenderUUID())) return;
        handle(received);
    }

    public abstract void handle(OARedisPacket received);

    @Setter @Getter private UUID senderUUID;

}
