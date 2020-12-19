package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;

public class VPacketPlayInKeepAlive extends APacketPlayInKeepAlive {
    
    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInKeepAlive) {
            this.handle(((PacketPlayInKeepAlive) packet));
        }
    }
    
    public void handle(PacketPlayInKeepAlive packet) {
        this.id = packet.a();
    }
}
