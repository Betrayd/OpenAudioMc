package com.craftmend.openaudiomc.generic.proxy.messages;

import java.util.UUID;

import lombok.Getter;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Represents a BungeeCord, a Velocity or Bukkit player
 * Created by iKeirNez on 12/12/13, ported to velocity and modified by fluse1367 on 11/2020.
 */
@Getter
public class PacketPlayer {

    private final String name;
    private final UUID uuid;
    // private ProxiedPlayer bungeePlayer = null;
    // private Player bukkitPlayer = null;
    // private com.velocitypowered.api.proxy.Player velocityPlayer = null;
    private ServerPlayerEntity fabricPlayer = null;

    public PacketPlayer(ServerPlayerEntity fabricPlayer) {
        name = fabricPlayer.getName().getString();
        uuid = fabricPlayer.getUuid();
        this.fabricPlayer = fabricPlayer;
    }
    

    // /**
    //  * Constructor used in the case of a Velocity player
    //  * @param velocityPlayer The Velocity player instance
    //  */
    // public PacketPlayer(com.velocitypowered.api.proxy.Player velocityPlayer){
    //     this.velocityPlayer = velocityPlayer;
    //     this.name = velocityPlayer.getUsername();
    //     this.uuid = velocityPlayer.getUniqueId();
    // }

    // /**
    //  * Constructor used in the case of a BungeeCord player
    //  * @param bungeePlayer The BungeeCord player instance
    //  */
    // public PacketPlayer(ProxiedPlayer bungeePlayer){
    //     this.bungeePlayer = bungeePlayer;
    //     this.name = bungeePlayer.getName();
    //     this.uuid = bungeePlayer.getUniqueId();
    // }

    // /**
    //  * Constructor used in the case of a Bukkit player
    //  * @param bukkitPlayer The Bukkit player instance
    //  */
    // public PacketPlayer(Player bukkitPlayer){
    //     this.bukkitPlayer = bukkitPlayer;
    //     this.name = bukkitPlayer.getName();
    //     this.uuid = bukkitPlayer.getUniqueId();
    // }

}
