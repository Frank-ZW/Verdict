package me.frankthedev.verdict.check.types;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.Check;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;

public abstract class RotationCheck extends Check {

    public RotationCheck(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    public abstract void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp);
}
