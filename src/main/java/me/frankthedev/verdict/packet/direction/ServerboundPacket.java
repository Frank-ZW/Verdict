package me.frankthedev.verdict.packet.direction;

import com.google.common.collect.BiMap;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.*;

import java.util.Map;

public enum ServerboundPacket {

    PLAY_CUSTOM_PAYLOAD(PacketPlayInCustomPayload.class),
    PLAY_ABILITIES(PacketPlayInAbilities.class),
    PLAY_WINDOW_CLICK(PacketPlayInWindowClick.class),
    PLAY_ENTITY_ACTION(PacketPlayInEntityAction.class),
    PLAY_BLOCK_DIG(PacketPlayInBlockDig.class),
    PLAY_BLOCK_PLACE(PacketPlayInBlockPlace.class),
    PLAY_USE_ENTITY(PacketPlayInUseEntity.class),
    PLAY_ARM_ANIMATION(PacketPlayInArmAnimation.class),
    PLAY_KEEP_ALIVE(PacketPlayInKeepAlive.class),
    PLAY_POSITION_LOOK(PacketPlayInFlying.PacketPlayInPositionLook.class),
    PLAY_LOOK(PacketPlayInFlying.PacketPlayInLook.class),
    PLAY_POSITION(PacketPlayInFlying.PacketPlayInPosition.class),
    PLAY_FLYING(PacketPlayInFlying.class);

    private final EnumProtocol protocol;
    private final Integer id;

    ServerboundPacket(Class<? extends Packet<?>> clazz) {
        Map<Class<? extends Packet<?>>, EnumProtocol> protocolMap = ReflectionUtil.getLocalField(EnumProtocol.class, null, "h");
        this.protocol = protocolMap.get(clazz);
        Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> protocolBiMap = ReflectionUtil.getLocalField(EnumProtocol.class, this.protocol, "j");
        this.id = protocolBiMap.get(EnumProtocolDirection.SERVERBOUND).inverse().get(clazz);
    }

    public Integer getId() {
        return this.id;
    }

    public Packet<?> getPacket() throws InstantiationException, IllegalAccessException {
        return this.protocol.a(EnumProtocolDirection.SERVERBOUND, this.id);
    }
}
