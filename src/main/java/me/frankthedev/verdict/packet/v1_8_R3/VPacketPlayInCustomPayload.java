package me.frankthedev.verdict.packet.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.frankthedev.verdict.packet.packets.APacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;

public class VPacketPlayInCustomPayload extends APacketPlayInCustomPayload {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInCustomPayload) {
            this.handle((PacketPlayInCustomPayload) packet);
        }
    }

    public void handle(PacketPlayInCustomPayload packet) {
        this.channel = packet.a();
        this.length = this.channel.length();
        ByteBuf byteBuf = Unpooled.copiedBuffer(packet.b());
        this.data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(this.data);
    }
}
