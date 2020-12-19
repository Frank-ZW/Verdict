package me.frankthedev.verdict.packet.direction;

import com.google.common.collect.BiMap;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.*;

import java.util.Map;

public enum ClientboundPacket {

    PLAY_SPAWN_POSITION(PacketPlayOutSpawnPosition.class),
    PLAY_POSITION(PacketPlayOutPosition.class),
    PLAY_ENTITY_VELOCITY(PacketPlayOutEntityVelocity.class),
    PLAY_EXPLOSION(PacketPlayOutExplosion.class),
    PLAY_KEEP_ALIVE(PacketPlayOutKeepAlive.class);

    private final EnumProtocol protocol;
    private final Integer id;

    ClientboundPacket(Class<? extends Packet<?>> clazz) {
        Map<Class<? extends Packet<?>>, EnumProtocol> protocolMap = ReflectionUtil.getLocalField(EnumProtocol.class, null, "h");
        this.protocol = protocolMap.get(clazz);
        Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> protocolBiMap = ReflectionUtil.getLocalField(EnumProtocol.class, this.protocol, "j");
        this.id = protocolBiMap.get(EnumProtocolDirection.CLIENTBOUND).inverse().get(clazz);
    }

    public Integer getId() {
        return this.id;
    }

    public Packet<?> getPacket() throws InstantiationException, IllegalAccessException {
        return this.protocol.a(EnumProtocolDirection.CLIENTBOUND, this.id);
    }
}
