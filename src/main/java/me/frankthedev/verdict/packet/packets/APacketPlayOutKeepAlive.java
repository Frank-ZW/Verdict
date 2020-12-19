package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayOutKeepAlive extends APacket {

    protected int ID;

    public int getID() {
        return this.ID;
    }
}
