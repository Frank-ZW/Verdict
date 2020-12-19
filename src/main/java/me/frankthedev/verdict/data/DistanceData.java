package me.frankthedev.verdict.data;

import me.frankthedev.verdict.util.Cuboid;
import me.frankthedev.verdict.util.location.PlayerLocation;

public class DistanceData {

    private final PlayerLocation location;
    private final double distanceX;
    private final double distanceY;
    private final double distanceZ;
    private final double distance;
    private final Cuboid hitbox;

    public DistanceData(PlayerLocation location, double distanceX, double distanceY, double distanceZ, double distance, Cuboid hitbox) {
        this.location = location;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.distanceZ = distanceZ;
        this.distance = distance;
        this.hitbox = hitbox;
    }

    public PlayerLocation getLocation() {
        return this.location;
    }

    public double getDistanceX() {
        return this.distanceX;
    }

    public double getDistanceY() {
        return this.distanceY;
    }

    public double getDistanceZ() {
        return this.distanceZ;
    }

    public double getDistance() {
        return this.distance;
    }

    public Cuboid getHitbox() {
        return this.hitbox;
    }
}
