package me.frankthedev.verdict.check.impl.reach;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.ReachCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.data.ReachData;
import org.bukkit.GameMode;

@CheckInfo(name = "Reach A", maxViolation = 8)
public class ReachA extends ReachCheck {

    private int preVL = 0;

    public ReachA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(ReachData reachData, long timestamp) {
        if (!player.getGameMode().equals(GameMode.CREATIVE) && timestamp - this.playerData.getLastDelayedTimestamp() > 110L && timestamp - this.playerData.getLastFlyingTimestamp() < 110L) {
            if (reachData.getReach() > 3.0625D) {
                if (++this.preVL > 2) {
                    this.handleViolation(String.format("Reach: %.3f", reachData.getReach()));
                    this.preVL = 0;
                }
            } else {
                this.decreaseVL(0.05D);
                this.preVL = 0;
            }
        }
    }
}
