package me.frankthedev.verdict.check.types;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.Check;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;

public abstract class PositionCheck extends Check {

    public PositionCheck(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    public abstract void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp);
}
