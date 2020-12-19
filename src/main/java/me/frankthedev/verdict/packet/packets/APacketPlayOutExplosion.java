package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayOutExplosion extends APacket {

    protected double x;
    protected double y;
    protected double z;
    protected float motionX;
    protected float motionY;
    protected float motionZ;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getMotionX() {
        return this.motionX;
    }

    public float getMotionY() {
        return this.motionY;
    }

    public float getMotionZ() {
        return this.motionZ;
    }
}
