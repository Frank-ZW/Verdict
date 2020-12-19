package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInBlockPlace;
import me.frankthedev.verdict.util.bukkit.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;

public class VPacketPlayInBlockPlace extends APacketPlayInBlockPlace {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInBlockPlace) {
            this.handle((PacketPlayInBlockPlace) packet);
        }
    }

    public void handle(PacketPlayInBlockPlace packet) {
        this.position = new BlockPosition(packet.a().getX(), packet.a().getY(), packet.a().getZ());
        this.face = packet.getFace();
        this.itemStack = packet.getItemStack();
        this.cursorX = packet.d();
        this.cursorY = packet.e();
        this.cursorZ = packet.f();
    }
}
