package me.frankthedev.verdict.util;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.util.bukkit.MutableBlockLocation;
import me.frankthedev.verdict.util.java.Pair;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.function.Predicate;
import java.util.logging.Level;

public class Cuboid {

    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;

    public Cuboid(PlayerLocation location) {
        this(location.getX(), location.getY(), location.getZ());
    }

    public Cuboid(double x, double y, double z) {
        this(x, y, z, x, y, z);
    }

    public Cuboid(PlayerLocation loc1, PlayerLocation loc2) {
        this(loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ());
    }

    public Cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    /**
     * Adds the specified parameters into the minimum and maximum
     * values of the cuboid.
     *
     * @param x1    The amount added to the minimum x-value
     * @param y1    The amount added to the minimum y-value
     * @param z1    The amount added to the minimum z-value
     * @param x2    The amount added to the maximum x-value
     * @param y2    The amount added to the maximum y-value
     * @param z2    The amount added to the maximum z-value
     * @return      The updated cuboid
     */
    public Cuboid add(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX += x1;
        this.minY += y1;
        this.minZ += z1;
        this.maxX += x2;
        this.maxY += y2;
        this.maxZ += z2;
        return this;
    }

    /**
     * Expands the given cuboid by the specified amount and
     * returns the instance.
     *
     * @param x The amount to expand the x component of the cuboid
     * @param y The amount to expand the y component of the cuboid
     * @param z the amount to expand the z component of the cuboid
     * @return  The expanded cuboid
     */
    public Cuboid expand(double x, double y, double z) {
        this.minX -= x;
        this.minY -= y;
        this.minZ -= z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    /**
     * Returns the cuboid translated by x, y, and z blocks
     *
     * @param x     The x-amount to move the cuboid by.
     * @param y     The y-amount to move the cuboid by.
     * @param z     The z-amount to move the cuboid by.
     * @return      The moved cuboid.
     */
    public Cuboid move(double x, double y, double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    public Pair<PlayerLocation, Double> getClosestPlayerLocation(PlayerLocation location1, PlayerLocation location2) {
        double distance1 = this.getMinGroundDistance(location1);
        double distance2 = this.getMinGroundDistance(location2);
        return distance1 < distance2 ? new Pair<>(location1, distance1) : new Pair<>(location2, distance2);
    }

    /**
     * Returns the x-coordinate of the cuboid that is closest
     * to PlayerLocation other.
     *
     * @param other The other PlayerLocation to be compared to.
     * @return      The x-coordinate of the closest corner to other.
     */
    public double getClosestCornerX(PlayerLocation other) {
        return Math.abs(this.minX - other.getX()) < Math.abs(this.maxX - other.getX()) ? this.minX : this.maxX;
    }

    /**
     * Returns the z-coordinate of the cuboid that is closest
     * to PlayerLocation other.
     *
     * @param other The other PlayerLocation to be compared to.
     * @return      The z-coordinate of the closest corner to other.
     */
    public double getClosestCornerZ(PlayerLocation other) {
        return Math.abs(this.minZ - other.getZ()) < Math.abs(this.maxZ - other.getZ()) ? this.minZ : this.maxZ;
    }

    /**
     * Returns the distance between the given coordinate and the closet corner
     * of the cuboid.
     *
     * @param other The other PlayerLocation coordinate
     * @return      The distance between the other coordinate and the closest edge of the cuboid
     */
    public double getMinGroundDistance(PlayerLocation other) {
        return this.getMinGroundDistance(other.getX(), other.getZ());
    }

    /**
     * Returns the distance between the given coordinates and the closest corner
     * of the cuboid.
     *
     * @param x The x-coordinate of the other cuboid
     * @param z The z-coordinate of the other location
     * @return  The distance between the other coordinate and the closest edge of the cuboid
     */
    public double getMinGroundDistance(double x, double z) {
        return Math.sqrt(Math.min(Math.pow(this.minX - x, 2.0D), Math.pow(this.maxX - x, 2.0D)) + Math.min(Math.pow(this.minZ - z, 2.0D), Math.pow(this.maxZ - z, 2.0D)));
    }

    /**
     * Returns the cuboid after being rotated by yaw degrees using
     * the formulas: x' = (x - p) cos t - (y - q) sin t + p and
     * y' = (x - p) sin t + (y - q) cos t + q where (p, q) is the
     * center coordinate, t is the amount to rotate by in degrees,
     * and (x, y) is the original starting coordinate.
     *
     * @param yaw   The amount to rotate the cuboid by in degrees.
     */
    public Cuboid rotate(float yaw) {
        this.minX = (this.minX - this.getCenterX()) * Math.cos(yaw) - (this.minZ - this.getCenterZ()) * Math.sin(yaw) + this.getCenterX();
        this.minZ = (this.minX - this.getCenterX()) * Math.sin(yaw) + (this.minZ - this.getCenterZ()) * Math.cos(yaw) + this.getCenterZ();
        this.maxX = (this.maxX - this.getCenterX()) * Math.cos(yaw) - (this.maxZ - this.getCenterZ()) * Math.sin(yaw) + this.getCenterX();
        this.maxZ = (this.maxX - this.getCenterX()) * Math.sin(yaw) + (this.maxZ - this.getCenterZ()) * Math.cos(yaw) + this.getCenterZ();
        return this;
    }

    public boolean checkBlocks(World world, Predicate<Material> predicate) {
        return this.checkInternalBlocks(world, location -> predicate.test(location.getType(world)));
    }

    public boolean checkInternalBlocks(World world, Predicate<MutableBlockLocation> predicate) {
        int blockMinX = (int) Math.floor(this.minX);
        int blockMinY = (int) Math.max(Math.floor(this.minY), 0);
        int blockMinZ = (int) Math.floor(this.minZ);
        int blockMaxX = (int) Math.ceil(this.maxX);
        int blockMaxY = (int) Math.min(Math.ceil(this.maxY), world.getMaxHeight());
        int blockMaxZ = (int) Math.ceil(this.maxZ);
        int volume = (blockMaxX - blockMinX) * (blockMaxZ - blockMinZ) * Math.max(blockMaxY - blockMinY, 1);
        if (volume > 5000) {
            Verdict.getInstance().getLogger().log(Level.SEVERE, "The volume of the cuboid exceeds the maximum limit of 5000 cubic blocks.");
            return false;
        }

        MutableBlockLocation blockLocation = new MutableBlockLocation(blockMinX, blockMinY, blockMinZ);
        while (blockLocation.getX() < blockMaxX) {
            while (blockLocation.getZ() < blockMaxZ) {
                while (blockLocation.getY() < blockMaxY) {
                    if (!predicate.test(blockLocation)) {
                        return false;
                    }

                    blockLocation.incrementY();
                }

                blockLocation.setY(blockMinY);
                blockLocation.incrementZ();
            }

            blockLocation.setY(blockMinY);
            blockLocation.setZ(blockMinZ);
            blockLocation.incrementX();
        }

        return true;
    }

    public double getCenterX() {
        return (this.minX + this.maxX) / 2.0D;
    }

    public double getCenterY() {
        return (this.minY + this.maxY) / 2.0D;
    }

    public double getCenterZ() {
        return (this.minZ + this.maxZ) / 2.0D;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMinZ() {
        return this.minZ;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public double getMaxZ() {
        return this.maxZ;
    }

    public String toString() {
        return String.format("cuboid [%s %s %s -> %s %s %s]", this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }
}
