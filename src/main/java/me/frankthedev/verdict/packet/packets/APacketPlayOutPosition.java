package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.util.location.PlayerLocation;

import java.util.Set;

public abstract class APacketPlayOutPosition extends APacket {

    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected Set<PlayerTeleportFlags> flags;

    public PlayerLocation toPlayerLocation(boolean onGround, int totalTicks) {
        return new PlayerLocation(this.x, this.y, this.z, this.yaw, this.pitch, onGround, totalTicks);
    }

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

    public Set<PlayerTeleportFlags> getFlags() {
        return this.flags;
    }

    public enum PlayerTeleportFlags {
        X,
        Y,
        Z,
        Y_ROT,
        X_ROT;

        PlayerTeleportFlags() {}
    }
}
