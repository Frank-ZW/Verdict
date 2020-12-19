package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Fly A", maxViolation = 18)
public class FlyA extends PositionCheck {

    private float limit = 2.0F;

    public FlyA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (!player.getAllowFlight() && !this.playerData.isInWeb() && !this.playerData.isInLiquid() && !this.playerData.isInVehicle() && !this.playerData.isOnGround() && !this.playerData.isClimbing() && this.playerData.getLastAttackTicks() > 10 && location.getY() > lastLocation.getY()) {
            double offsetY = location.getY() - this.playerData.getLastGroundY();
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.JUMP)) {
                    int amplifier = effect.getAmplifier() + 1;
                    this.limit += Math.pow((double) amplifier + 4.2D, 2.0D) / 16.0D;
                    break;
                }
            }

            if (offsetY > limit) {
                this.handleViolation(String.format("D: %.3f", offsetY));
            } else {
                this.decreaseVL(0.05);
            }
        } else {
            this.decreaseVL(0.05);
        }
    }
}
