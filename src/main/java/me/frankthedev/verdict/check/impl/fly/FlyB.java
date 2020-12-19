package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;

@CheckInfo(name = "Fly B", maxViolation = 20)
public class FlyB extends PositionCheck {

    private int lastGroundTick;

    public FlyB(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (!player.getAllowFlight() && !this.playerData.isInLiquid() && !this.playerData.isInWeb() && this.playerData.getLastAttackTicks() > 20) {
            if (this.playerData.isOnGround()) {
                this.lastGroundTick = this.playerData.getTotalTicks();
                return;
            }

            if (this.playerData.getTotalTicks() - this.lastGroundTick > 12) {
                double offsetH = location.getSquaredGroundDistance(lastLocation);
                double offsetY = location.getY() - lastLocation.getY();
                if (offsetH > 0 && offsetY == 0) {
                    this.handleViolation(String.format("Horizontal: %s. Vertical: %s.", offsetH, offsetY));
                } else {
                    this.decreaseVL(0.05D);
                }
            }
        } else {
            this.decreaseVL(0.05D);
        }
    }
}
