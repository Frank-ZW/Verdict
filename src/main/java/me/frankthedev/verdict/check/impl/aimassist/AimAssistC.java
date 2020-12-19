package me.frankthedev.verdict.check.impl.aimassist;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.RotationCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;

@CheckInfo(name = "Aim Assist C", maxViolation = 20)
public class AimAssistC extends RotationCheck {

    private float avgYawDifference = 0.0F;
    private float avgPitchDifference = 0.0F;
    private boolean lastLookedDown;

    public AimAssistC(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        boolean lookedDown = lastLocation.getPitch() > location.getPitch();
        if (this.playerData.getLastAttackTicks() <= 50) {
            float yawDifference = Math.abs(lastLocation.getYaw() - location.getYaw());
            float pitchDifference = Math.abs(lastLocation.getPitch() - location.getPitch());
            this.avgYawDifference = (this.avgYawDifference * 4.0F + yawDifference) / 5.0F;
            if ((double) pitchDifference > 0.001D && (double) pitchDifference < 0.01D && (double) yawDifference > 0.01D && lookedDown != this.lastLookedDown) {
                this.handleViolation();
            } else {
                this.decreaseVL(0.001D);
            }

            this.avgPitchDifference = (this.avgPitchDifference * 4.0F + pitchDifference) / 5.0F;
        }

        this.lastLookedDown = lookedDown;
    }
}
