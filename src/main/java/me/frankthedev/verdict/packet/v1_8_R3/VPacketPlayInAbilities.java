package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;

public class VPacketPlayInAbilities extends APacketPlayInAbilities {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInAbilities) {
            this.handle((PacketPlayInAbilities) packet);
        }
    }

    public void handle(PacketPlayInAbilities packet) {
        this.invulnerable = packet.a();
        this.flying = packet.isFlying();
        this.allowedFlight = packet.c();
        this.creative = packet.d();
    }
}
