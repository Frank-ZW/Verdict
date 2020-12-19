package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayOutKeepAlive;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;

public class VPacketPlayOutKeepAlive extends APacketPlayOutKeepAlive {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayOutKeepAlive) {
            this.handle(((PacketPlayOutKeepAlive) packet));
        }
    }

    public void handle(PacketPlayOutKeepAlive packet) {
        this.ID = ReflectionUtil.getLocalField(PacketPlayOutKeepAlive.class, packet, "a");
    }
}
