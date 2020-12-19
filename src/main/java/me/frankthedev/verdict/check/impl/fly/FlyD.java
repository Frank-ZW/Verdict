package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;

@CheckInfo(name = "Fly D", maxViolation = 20)
public class FlyD extends PositionCheck {

    private boolean lastOnGround;

    public FlyD(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (!player.getAllowFlight() && !this.playerData.isInLiquid() && !this.playerData.isInWeb() && !this.playerData.isUnderBlock() && this.playerData.getLastAttackTicks() > 10 && location.getY() > lastLocation.getY()) {
            float jumpDistY = 0.42F;
            boolean onGround = lastLocation.isOnGround();
            double distY = location.getY() - lastLocation.getY();
            if (Math.abs(distY - jumpDistY) < 0.0001) {
                if (!onGround && !this.lastOnGround) {
                    this.handleViolation("", 2.0D);
                } else {
                    this.decreaseVL(1.0D);
                }
            }

            this.lastOnGround = onGround;
        }
    }
}
