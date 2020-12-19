package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayOutEntityVelocity extends APacket {

    protected int entityID;
    protected double motionX;
    protected double motionY;
    protected double motionZ;

    public int getEntityID() {
        return this.entityID;
    }

    public double getMotionX() {
        return this.motionX;
    }

    public double getMotionY() {
        return this.motionY;
    }

    public double getMotionZ() {
        return this.motionZ;
    }
}
