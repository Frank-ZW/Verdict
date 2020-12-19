package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInPositionLook;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class VPacketPlayInPositionLook extends APacketPlayInPositionLook {
    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInPositionLook) {
            this.handle((PacketPlayInFlying.PacketPlayInPositionLook) packet);
        }
    }

    public void handle(PacketPlayInFlying.PacketPlayInPositionLook packet) {
        this.x = packet.a();
        this.y = packet.b();
        this.z = packet.c();
        this.yaw = packet.d();
        this.pitch = packet.e();
        this.onGround = packet.f();
        this.pos = true;
        this.look = true;
    }
}
