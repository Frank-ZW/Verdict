package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayOutEntityVelocity;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;

public class VPacketPlayOutEntityVelocity extends APacketPlayOutEntityVelocity {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayOutEntityVelocity) {
            this.handle((PacketPlayOutEntityVelocity) packet);
        }
    }

    public void handle(PacketPlayOutEntityVelocity packet) {
        this.entityID = ReflectionUtil.getLocalField(PacketPlayOutEntityVelocity.class, packet, "a");
        this.motionX = ((double) (int) ReflectionUtil.getLocalField(PacketPlayOutEntityVelocity.class, packet, "b")) / 8000.0D;
        this.motionY = ((double) (int) ReflectionUtil.getLocalField(PacketPlayOutEntityVelocity.class, packet, "c")) / 8000.0D;
        this.motionZ = ((double) (int) ReflectionUtil.getLocalField(PacketPlayOutEntityVelocity.class, packet, "d")) / 8000.0D;
    }
}
