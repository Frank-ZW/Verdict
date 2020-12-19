package me.frankthedev.verdict.util.location;

import me.frankthedev.verdict.util.Cuboid;
import me.frankthedev.verdict.util.Vector;
import me.frankthedev.verdict.util.bukkit.MutableBlockLocation;
import org.bukkit.Location;
import org.bukkit.World;

public class PlayerLocation {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private int totalTicks;
    private long timestamp;

    public PlayerLocation(double x, double y, double z, float yaw, float pitch, boolean onGround, int totalTicks) {
        this(x, y, z, yaw, pitch, onGround, totalTicks, System.currentTimeMillis());
    }

    public PlayerLocation(double x, double y, double z, float yaw, float pitch, boolean onGround, int totalTicks, long timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.totalTicks = totalTicks;
        this.timestamp = timestamp;
    }

    public static PlayerLocation fromBukkitLocation(Location location) {
        return new PlayerLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getHighestBlockYAt(location) == location.getBlockY(), 0);
    }

    public Location toBukkitLocation(World world) {
        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public boolean sameLocation(PlayerLocation other) {
        return this.x == other.getX() && this.y == other.getY() && this.z == other.getZ();
    }

    public boolean sameDirection(PlayerLocation other) {
        return this.yaw == other.getYaw() && this.pitch == other.getPitch();
    }

    public double getGroundDistance(PlayerLocation other) {
        return Math.sqrt(this.getSquaredGroundDistance(other));
    }

    public double getSquaredGroundDistance(PlayerLocation other) {
        return Math.pow(this.x - other.getX(), 2.0D) + Math.pow(this.z - other.getZ(), 2.0D);
    }

    public double getDistance(PlayerLocation other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2.0D) + Math.pow(this.y - other.getY(), 2.0D) + Math.pow(this.z - other.getZ(), 2.0D));
    }

    public PlayerLocation cloneLocation() {
        return new PlayerLocation(this.x, this.y, this.z, this.yaw, this.pitch, this.onGround, this.totalTicks, this.timestamp);
    }

    public MutableBlockLocation getMutableBlockLocation() {
        return new MutableBlockLocation((int) Math.floor(this.x), (int) Math.floor(this.y), (int) Math.floor(this.z));
    }

    public Vector getDirection() {
        double xz = Math.cos(Math.toRadians(this.pitch));
        double x = -xz * Math.sin(Math.toRadians(this.yaw));
        double y = -Math.sin(Math.toRadians(this.pitch));
        double z = xz * Math.cos(Math.toRadians(this.yaw));
        return new Vector(x, y, z);
    }

    public Vector getVelocity(PlayerLocation other) {
        return new Vector(other.getX() - this.x, other.getY() - this.y, other.getZ() - this.z);
    }

    public Cuboid getBoundingBox() {
        return new Cuboid(this).add(-0.3D, 0.0D, -0.3D, 0.3D, 1.8D, 0.3D);
    }

    public Cuboid getHitbox() {
        return this.getBoundingBox().expand(0.1D, 0.1D, 0.1D);
    }

    public PlayerLocation subtract(PlayerLocation other) {
        this.x -= other.getX();
        this.y -= other.getY();
        this.z -= other.getZ();
        return this;
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
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

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
