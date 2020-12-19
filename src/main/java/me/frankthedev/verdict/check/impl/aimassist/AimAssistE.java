package me.frankthedev.verdict.check.impl.aimassist;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.RotationCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;

@CheckInfo(name = "Aim Assist E", maxViolation = 8)
public class AimAssistE extends RotationCheck {

    private float lastPitchDifference;
    private float lastYawDifference;

    public AimAssistE(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (this.playerData.getLastAttackTicks() <= 200) {
            float yawDifference = Math.abs(location.getYaw() - lastLocation.getYaw());
            float pitchDifference = Math.abs(location.getPitch() - lastLocation.getPitch());
            float pitchDifferenceRate = Math.abs(this.lastPitchDifference - pitchDifference);
            float yawDifferenceRate = Math.abs(this.lastYawDifference - yawDifference);
            float pitchDifferenceRatePitch = Math.abs(pitchDifferenceRate - pitchDifference);
            float yawDifferenceRateYaw = Math.abs(yawDifferenceRate - yawDifference);
            if ((double) pitchDifference > 0.0D && (double) pitchDifference < 0.0094D && (double) pitchDifferenceRate > 1.0D && (double) yawDifferenceRate > 1.0D && (double) yawDifference > 3.0D && (double) this.lastYawDifference > 1.5D && (pitchDifferenceRatePitch > 1.0F || yawDifferenceRateYaw > 1.0F)) {
                this.handleViolation(String.format("Pitch Diff: %s Pitch Diff Rate: %s", pitchDifference, pitchDifferenceRate));
            }

            this.lastYawDifference = yawDifference;
            this.lastPitchDifference = pitchDifference;
        }
    }
}
