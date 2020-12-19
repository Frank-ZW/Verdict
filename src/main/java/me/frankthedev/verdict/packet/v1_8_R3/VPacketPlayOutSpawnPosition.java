package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayOutSpawnPosition;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnPosition;

public class VPacketPlayOutSpawnPosition extends APacketPlayOutSpawnPosition {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayOutSpawnPosition) {
            this.handle((PacketPlayOutSpawnPosition) packet);
        }
    }

    public void handle(PacketPlayOutSpawnPosition packet) {
        BlockPosition position = ReflectionUtil.getLocalField(PacketPlayOutSpawnPosition.class, packet, "position");
        this.blockX = position.getX();
        this.blockY = position.getY();
        this.blockZ = position.getZ();
    }
}
