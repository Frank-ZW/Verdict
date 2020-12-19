package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Fly C", maxViolation = 16)
public class FlyC extends PositionCheck {

    private int illegalMovements;
    private int legalMovements;

    public FlyC(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (!player.getAllowFlight() && !this.playerData.isInVehicle() && this.playerData.getLastAttackTicks() > 10) {
            double offsetH = location.getGroundDistance(lastLocation);
            int speed = 0;
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.SPEED)) {
                    speed = effect.getAmplifier() + 1;
                    break;
                }
            }

            double limit;
            if (this.playerData.isOnStairs()) {
                limit = 0.45D;
            } else if (this.playerData.isOnIce()) {
                if (this.playerData.isUnderBlock()) {
                    limit = 1.3D;
                } else {
                    limit = 0.65D;
                }
            } else if (this.playerData.isUnderBlock()) {
                limit = 0.7D;
            } else {
                if (this.playerData.isOnGround()) {
                    limit = 0.34D;
                    limit += 0.06D * (double) speed;
                } else {
                    limit = 0.36D;
                    limit += 0.02D * (double) speed;
                }

                limit += player.getWalkSpeed() > 0.2F ? player.getWalkSpeed() * 10.0F * 0.33F : 0.0F;
                limit += (this.playerData.isOnGround() ? 0.06D : 0.02D) * (double) speed;
            }
//
//
//
//            if (this.playerData.isOnGround()) {
//                limit = 0.34D;
//                if (this.playerData.isOnStairs()) {
//                    limit = 0.45D;
//                } else if (this.playerData.isOnIce()) {
//                    if (this.playerData.isUnderBlock()) {
//                        limit = 1.3D;
//                    } else {
//                        limit = 0.65D;
//                    }
//                } else if (this.playerData.isUnderBlock()) {
//                    limit = 0.7D;
//                }
//
//                limit += player.getWalkSpeed() > 0.2F ? player.getWalkSpeed() * 10.0F * 0.33F : 0.0F;
//                limit += 0.06D * (double) speed;
//            } else {
//                limit = 0.36D;
//                if (this.playerData.isOnStairs()) {
//                    limit = 0.45D;
//                } else if (this.playerData.isOnIce()) {
//                    if (this.playerData.isUnderBlock()) {
//                        limit = 1.3D;
//                    } else {
//                        limit = 0.65D;
//                    }
//                } else if (this.playerData.isUnderBlock()) {
//                    limit = 0.7D;
//                }
//
//                limit += player.getWalkSpeed() > 0.2F ? player.getWalkSpeed() * 10.0F * 0.33F : 0.0F;
//                limit += 0.02D * (double) speed;
//            }

            if (offsetH > limit) {
                this.illegalMovements++;
            } else {
                this.legalMovements++;
            }

            int total = this.illegalMovements + this.legalMovements;
            if (total > 20) {
                double percentage = (double) this.illegalMovements / total;
                if (percentage > 80.0D) {
                    this.handleViolation(String.format("Percentage: %.3f", percentage), 2.0D);
                } else if (percentage > 45.0D) {
                    this.handleViolation(String.format("Percentage: %.3f", percentage));
                } else {
                    this.decreaseVL(0.5D);
                }

                this.illegalMovements = 0;
                this.legalMovements = 0;
            }
        }
    }
}
