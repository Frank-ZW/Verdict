package me.frankthedev.verdict.util.bukkit;

public class Velocity {

    private double x;
    private double y;
    private double z;
    private int totalTicks;
    private final long timestamp;

    public Velocity(double x, double y, double z, int totalTicks, long timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.totalTicks = totalTicks;
        this.timestamp = timestamp;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getTotalTicks() {
        return this.totalTicks;
    }

    public void setTotalTicks(int totalTicks) {
        this.totalTicks = totalTicks;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
