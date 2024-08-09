package com.craftmend.openaudiomc.spigot.modules.speakers.objects;

import com.craftmend.openaudiomc.api.speakers.Loc;
import com.craftmend.openaudiomc.generic.utils.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.block.Block;
import net.minecraft.world.World;

@Data
@AllArgsConstructor
public class MappedLocation implements Loc {

    private int x;
    private int y;
    private int z;
    private String world;

    public MappedLocation(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = location.getWorld().getRegistryKey().getValue().toString();
    }

    public Location toBukkit() {
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
    }

    public Block getBlock() {
        World world = Bukkit.getWorld(this.world);
        if (world != null) return world.getBlockAt(this.x, this.y, this.z);
        return null;
    }

    public static MappedLocation fromBukkit(Location location) {
        return new MappedLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName());
    }

}
