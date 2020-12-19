package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInLook;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class VPacketPlayInLook extends APacketPlayInLook {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
            this.handle((PacketPlayInFlying.PacketPlayInLook) packet);
        }
    }

    public void handle(PacketPlayInFlying.PacketPlayInLook packet) {
        this.x = packet.a();
        this.y = packet.b();
        this.z = packet.c();
        this.yaw = packet.d();
        this.pitch = packet.e();
        this.onGround = packet.f();
        this.pos = false;
        this.look = true;
    }
}
