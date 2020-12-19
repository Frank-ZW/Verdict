package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;

public class VPacketPlayInWindowClick extends APacketPlayInWindowClick {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInWindowClick) {
            this.handle((PacketPlayInWindowClick) packet);
        }
    }

    public void handle(PacketPlayInWindowClick packet) {

    }
}
