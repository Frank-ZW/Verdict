package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayInFlying extends APacket {

    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected boolean onGround;
    protected boolean pos;
    protected boolean look;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public boolean isPos() {
        return this.pos;
    }

    public boolean isLook() {
        return this.look;
    }
}
