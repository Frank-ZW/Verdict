package me.frankthedev.verdict.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.manager.PacketManager;
import net.minecraft.server.v1_8_R3.Packet;

import java.util.logging.Level;

public class PacketHandler extends ChannelDuplexHandler {

    private final PlayerData playerData;
    private final PacketManager packetManager;

    public PacketHandler(PlayerData playerData, PacketManager packetManager) {
        this.playerData = playerData;
        this.packetManager = packetManager;
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
        super.write(channelHandlerContext, packet, channelPromise);
        try {
            this.handle(channelHandlerContext, (Packet<?>) packet, false);
        } catch (Throwable t) {
            Verdict.getInstance().getServer().getLogger().log(Level.SEVERE, "Failed to handle outgoing packet for " + this.playerData.getName() + " ", t);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
        super.channelRead(channelHandlerContext, packet);
        try {
            this.handle(channelHandlerContext, (Packet<?>) packet, true);
        } catch (Throwable t) {
            Verdict.getInstance().getServer().getLogger().log(Level.SEVERE, "Failed to handle incoming packet for " + this.playerData.getName() + " ", t);
        }
    }

    public void handle(ChannelHandlerContext channelDuplexHandler, Packet<?> packet, boolean serverbound) {
        APacket aPacket = this.packetManager.decode(channelDuplexHandler, packet, serverbound);
        if (aPacket != null) {
            this.playerData.processPacket(aPacket, serverbound);
        }
    }
}
