package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInPosition;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class VPacketPlayInPosition extends APacketPlayInPosition {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInPosition) {
            this.handle((PacketPlayInFlying.PacketPlayInPosition) packet);
        }
    }

    public void handle(PacketPlayInFlying.PacketPlayInPosition packet) {
        this.x = packet.a();
        this.y = packet.b();
        this.z = packet.c();
        this.yaw = packet.d();
        this.pitch = packet.e();
        this.onGround = packet.f();
        this.pos = true;
        this.look = false;
    }
}
