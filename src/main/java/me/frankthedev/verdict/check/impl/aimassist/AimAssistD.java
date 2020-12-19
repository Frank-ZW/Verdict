package me.frankthedev.verdict.check.impl.aimassist;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.MathUtil;
import me.frankthedev.verdict.util.location.PlayerLocation;

import java.util.ArrayList;
import java.util.List;

@CheckInfo(name = "Aim Assist D", maxViolation = 16)
public class AimAssistD extends PositionCheck {

    private final List<Long> pitchDifferences = new ArrayList<>();
    private Long lastPitchGCD = null;

    public AimAssistD(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        long pitchDifference = (long) Math.abs(location.getPitch() - lastLocation.getPitch());
        if (pitchDifference == 90.0F || pitchDifference == 0.0F) {
            return;
        }

        this.pitchDifferences.add(pitchDifference);
        if (this.pitchDifferences.size() > 20) {
            long gcd = MathUtil.gcdLong(this.pitchDifferences);
            if (this.lastPitchGCD != null) {
                float gcdDifference = Math.abs(gcd - this.lastPitchGCD);
                if (gcdDifference > 0.001F || gcd < 0.00001F) {
                    this.handleViolation();
                } else {
                    this.decreaseVL(0.001D);
                }

                this.pitchDifferences.clear();
            }

            this.lastPitchGCD = gcd;
        }
    }
}
