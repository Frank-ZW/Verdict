package me.frankthedev.verdict.data;

import me.frankthedev.verdict.util.location.PlayerLocation;

public class ReachData {

    private final PlayerData playerData;
    private final PlayerLocation location;
    private final DistanceData distanceData;
    private final double maxDistance;
    private final double minDistance;
    private final double horizontal;
    private final double vertical;
    private final double extra;
    private final double reach;

    public ReachData(PlayerData playerData, PlayerLocation location, DistanceData distanceData, double maxDistance, double minDistance, double horizontal, double vertical, double extra, double reach) {
        this.playerData = playerData;
        this.location = location;
        this.distanceData = distanceData;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.extra = extra;
        this.reach = reach;
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public PlayerLocation getLocation() {
        return this.location;
    }

    public DistanceData getDistanceData() {
        return this.distanceData;
    }

    public double getMaxDistance() {
        return this.maxDistance;
    }

    public double getMinDistance() {
        return this.minDistance;
    }

    public double getHorizontal() {
        return this.horizontal;
    }

    public double getVertical() {
        return this.vertical;
    }

    public double getExtra() {
        return this.extra;
    }

    public double getReach() {
        return this.reach;
    }
}
