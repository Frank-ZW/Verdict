package me.frankthedev.verdict.util.bukkit;

import me.frankthedev.verdict.util.Vector;
import org.bukkit.Location;

public class BlockPosition {

    private int blockX;
    private int blockY;
    private int blockZ;

    public BlockPosition(int blockX, int blockY, int blockZ) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

    public boolean sameLocation(Location location) {
        return this.blockX == location.getBlockX() && this.blockY == location.getBlockY() && this.blockZ == location.getBlockZ();
    }

    public void setValues(Vector vector) {
        this.blockX = (int) Math.floor(vector.getX());
        this.blockY = (int) Math.floor(vector.getY());
        this.blockZ = (int) Math.floor(vector.getZ());
    }

    public BlockPosition add(int x, int y, int z) {
        this.blockX += x;
        this.blockY += y;
        this.blockZ += z;
        return this;
    }

    public BlockPosition incrementY() {
        return this.add(0, 1, 0);
    }

    public BlockPosition incrementX() {
        return this.add(1, 0, 0);
    }

    public BlockPosition incrementZ() {
        return this.add(0, 0, 1);
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

    public void setBlockX(int blockX) {
        this.blockX = blockX;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
    }

    public void setBlockZ(int blockZ) {
        this.blockZ = blockZ;
    }
}
