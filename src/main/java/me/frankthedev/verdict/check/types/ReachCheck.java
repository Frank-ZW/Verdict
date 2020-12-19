package me.frankthedev.verdict.check.types;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.Check;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.data.ReachData;

public abstract class ReachCheck extends Check {

    public ReachCheck(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    public abstract void handle(ReachData reachData, long timestamp);
}
