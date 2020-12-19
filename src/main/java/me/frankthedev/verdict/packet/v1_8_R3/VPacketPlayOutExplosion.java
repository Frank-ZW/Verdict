package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayOutExplosion;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;

public class VPacketPlayOutExplosion extends APacketPlayOutExplosion {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayOutExplosion) {
            this.handle((PacketPlayOutExplosion) packet);
        }
    }

    public void handle(PacketPlayOutExplosion packet) {
        this.x = ReflectionUtil.getLocalField(PacketPlayOutExplosion.class, packet, "a");
        this.y = ReflectionUtil.getLocalField(PacketPlayOutExplosion.class, packet, "b");
        this.z = ReflectionUtil.getLocalField(PacketPlayOutExplosion.class, packet, "c");
        this.motionX = ReflectionUtil.getLocalField(PacketPlayOutExplosion.class, packet, "f");
        this.motionY = ReflectionUtil.getLocalField(PacketPlayOutExplosion.class, packet, "g");
        this.motionZ = ReflectionUtil.getLocalField(PacketPlayOutExplosion.class, packet, "h");
    }
}
