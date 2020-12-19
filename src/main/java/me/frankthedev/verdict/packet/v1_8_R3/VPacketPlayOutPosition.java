package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayOutPosition;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;

public class VPacketPlayOutPosition extends APacketPlayOutPosition {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayOutPosition) {
            this.handle((PacketPlayOutPosition) packet);
        }
    }

    public void handle(PacketPlayOutPosition packet) {
        this.x = ReflectionUtil.getLocalField(PacketPlayOutPosition.class, packet, "a");
        this.y = ReflectionUtil.getLocalField(PacketPlayOutPosition.class, packet, "b");
        this.z = ReflectionUtil.getLocalField(PacketPlayOutPosition.class, packet, "c");
        this.yaw = ReflectionUtil.getLocalField(PacketPlayOutPosition.class, packet, "d");
        this.pitch = ReflectionUtil.getLocalField(PacketPlayOutPosition.class, packet, "e");
        this.flags = ReflectionUtil.getLocalField(PacketPlayOutPosition.class, packet, "f");
    }
}
