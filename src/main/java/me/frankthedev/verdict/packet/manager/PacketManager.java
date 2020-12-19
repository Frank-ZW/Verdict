package me.frankthedev.verdict.packet.manager;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.PacketHandler;
import me.frankthedev.verdict.packet.PacketDecoder;
import me.frankthedev.verdict.packet.v1_8_R3.*;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.direction.ClientboundPacket;
import me.frankthedev.verdict.packet.direction.ServerboundPacket;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import javax.annotation.Nullable;

public class PacketManager {

    private final AttributeKey<EnumProtocol> currentStateAttrKey;
    private final PacketDecoder[] incomingPackets;
    private final PacketDecoder[] outgoingPackets;
    private static PacketManager instance;

    public PacketManager() {
        this.currentStateAttrKey = NetworkManager.c;
        this.incomingPackets = new PacketDecoder[256 * EnumProtocol.values().length];
        this.outgoingPackets = new PacketDecoder[256 * EnumProtocol.values().length];

        this.registerOutgoing(EnumProtocol.PLAY, ClientboundPacket.PLAY_SPAWN_POSITION.getId(), VPacketPlayOutSpawnPosition.class);
        this.registerOutgoing(EnumProtocol.PLAY, ClientboundPacket.PLAY_POSITION.getId(), VPacketPlayOutPosition.class);
        this.registerOutgoing(EnumProtocol.PLAY, ClientboundPacket.PLAY_EXPLOSION.getId(), VPacketPlayOutExplosion.class);
        this.registerOutgoing(EnumProtocol.PLAY, ClientboundPacket.PLAY_ENTITY_VELOCITY.getId(), VPacketPlayOutEntityVelocity.class);
        this.registerOutgoing(EnumProtocol.PLAY, ClientboundPacket.PLAY_KEEP_ALIVE.getId(), VPacketPlayOutKeepAlive.class);

        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_CUSTOM_PAYLOAD.getId(), VPacketPlayInCustomPayload.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_ABILITIES.getId(), VPacketPlayInAbilities.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_WINDOW_CLICK.getId(), VPacketPlayInWindowClick.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_ENTITY_ACTION.getId(), VPacketPlayInEntityAction.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_BLOCK_PLACE.getId(), VPacketPlayInBlockPlace.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_BLOCK_DIG.getId(), VPacketPlayInBlockDig.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_USE_ENTITY.getId(), VPacketPlayInUseEntity.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_ARM_ANIMATION.getId(), VPacketPlayInArmAnimation.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_KEEP_ALIVE.getId(), VPacketPlayInKeepAlive.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_FLYING.getId(), VPacketPlayInFlying.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_LOOK.getId(), VPacketPlayInLook.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_POSITION.getId(), VPacketPlayInPosition.class);
        this.registerIncoming(EnumProtocol.PLAY, ServerboundPacket.PLAY_POSITION_LOOK.getId(), VPacketPlayInPositionLook.class);
    }

    public static PacketManager getInstance() {
        return PacketManager.instance == null ? PacketManager.instance = new PacketManager() : PacketManager.instance;
    }

    public EntityPlayer getEntityPlayer(PlayerData playerData) {
        return ((CraftPlayer) playerData.getPlayer()).getHandle();
    }

    public void injectPlayerData(PlayerData playerData) {
        Channel channel = this.getEntityPlayer(playerData).playerConnection.networkManager.channel;
        if (channel != null) {
            channel.pipeline().addBefore("packet_handler", "verdict-handler", new PacketHandler(playerData, this));
        }
    }

    public void uninjectPlayerData(PlayerData playerData) {
        PlayerConnection connection = this.getEntityPlayer(playerData).playerConnection;
        if (connection != null && !connection.isDisconnected()) {
            Channel channel = connection.networkManager.channel;
            try {
                channel.pipeline().remove("verdict-handler");
            } catch (Throwable ignored) {}
        }
    }

    @Nullable
    public APacket decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, boolean serverbound) {
        EnumProtocol protocol = channelHandlerContext.channel().attr(this.currentStateAttrKey).get();
        Integer id = protocol.a(serverbound ? EnumProtocolDirection.SERVERBOUND : EnumProtocolDirection.CLIENTBOUND, packet);
        if (id != null) {
            PacketDecoder packetDecoder = (serverbound ? this.incomingPackets : this.outgoingPackets)[this.toKey(protocol, id)];
            if (packetDecoder != null) {
                APacket aPacket = packetDecoder.get();
                aPacket.accept(packet);
                return aPacket;
            }
        }

        return null;
    }

    public int toKey(EnumProtocol protocol, int id) {
        return protocol.ordinal() << 8 | id;
    }

    public void registerIncoming(EnumProtocol protocol, Integer id, Class<? extends APacket> clazz) {
        this.incomingPackets[this.toKey(protocol, id)] = new PacketDecoder(clazz);
    }

    public void registerOutgoing(EnumProtocol protocol, Integer id, Class<? extends APacket> clazz) {
        this.outgoingPackets[this.toKey(protocol, id)] = new PacketDecoder(clazz);
    }
}
