package me.frankthedev.verdict.packet.manager;

import me.frankthedev.verdict.util.bukkit.MutableBlockLocation;
import me.frankthedev.verdict.util.location.PlayerLocation;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;

public class NMSManager {

    private static NMSManager instance;

    public static NMSManager getInstance() {
        return NMSManager.instance == null ? NMSManager.instance = new NMSManager() : NMSManager.instance;
    }
    
    public Material getType(World world, PlayerLocation location) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        BlockPosition position = new BlockPosition((int) Math.floor(location.getX()), (int) Math.floor(location.getY() + 1), (int) Math.floor(location.getZ()));
        if (worldServer.isLoaded(position)) {
            return CraftMagicNumbers.getMaterial(worldServer.getType(position).getBlock());
        }

        return Material.AIR;
    }

    public Material getType(World world, MutableBlockLocation location) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        if (worldServer.isLoaded(position)) {
            return CraftMagicNumbers.getMaterial(worldServer.getType(position).getBlock());
        }

        return Material.AIR;
    }
}
