package me.frankthedev.verdict.packet;

public class PacketDecoder extends ThreadLocal<APacket> {

    private final Class<? extends APacket> clazz;

    public PacketDecoder(Class<? extends APacket> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected APacket initialValue() {
        try {
            return clazz.asSubclass(APacket.class).getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
