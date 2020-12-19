package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class VPacketPlayInFlying extends APacketPlayInFlying {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInFlying) {
            this.handle((PacketPlayInFlying) packet);
        }
    }

    public void handle(PacketPlayInFlying packet) {
        this.x = packet.a();
        this.y = packet.b();
        this.z = packet.c();
        this.yaw = packet.d();
        this.pitch = packet.e();
        this.onGround = packet.f();
        this.pos = false;
        this.look = false;
    }
}
