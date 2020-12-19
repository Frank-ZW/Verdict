package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.util.location.PlayerLocation;

public abstract class APacketPlayOutSpawnPosition extends APacket {

    protected int blockX;
    protected int blockY;
    protected int blockZ;

    public PlayerLocation toPlayerLocation(boolean onGround, int totalTicks) {
        return new PlayerLocation(this.blockX, this.blockY, this.blockZ, 0.0F, 0.0F, onGround, totalTicks);
    }

    public int getBlockX() {
        return this.blockX;
    }

    public int getBlockY() {
        return this.blockY;
    }

    public int getBlockZ() {
        return this.blockZ;
    }
}
