package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayInCustomPayload extends APacket {

    protected String channel;
    protected int length;
    protected byte[] data;

    public String getChannel() {
        return this.channel;
    }

    public int getLength() {
        return this.length;
    }

    public byte[] getData() {
        return this.data;
    }
}
