package me.frankthedev.verdict.check.impl.aimassist;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.RotationCheck;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;
import me.frankthedev.verdict.util.MathUtil;

@CheckInfo(name = "Aim Assist B", maxViolation = 8)
public class AimAssistB extends RotationCheck {

    public AimAssistB(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (timestamp - this.playerData.getLastAttackTimestamp() < 10000L) {
            double deltaYaw = MathUtil.getAngleSeparation(location.getYaw(), lastLocation.getYaw());
            if (location.getPitch() == lastLocation.getPitch() && deltaYaw >= 3.0D && location.getPitch() != 90.0F && lastLocation.getPitch() != 90.0F) {
                this.handleViolation(String.format("Difference: %.3f", deltaYaw));
            } else {
                this.decreaseVL(1.0D);
            }
        }
    }
}
