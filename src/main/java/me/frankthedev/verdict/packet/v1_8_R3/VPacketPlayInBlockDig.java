package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

public class VPacketPlayInBlockDig extends APacketPlayInBlockDig {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInBlockDig) {
            this.handle((PacketPlayInBlockDig) packet);
        }
    }

    public void handle(PacketPlayInBlockDig packet) {
        this.position = packet.a();
        this.face = packet.b();
        this.type = PlayerDigType.values()[packet.c().ordinal()];
    }
}
