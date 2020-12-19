package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayInKeepAlive extends APacket {

    protected int id;

    public int getId() {
        return this.id;
    }
}
