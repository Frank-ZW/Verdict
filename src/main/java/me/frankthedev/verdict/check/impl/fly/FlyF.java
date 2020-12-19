package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.Cuboid;
import me.frankthedev.verdict.util.bukkit.BlockUtil;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Fly F", maxViolation = 20)
public class FlyF extends PositionCheck {

    private int lastBypassTick = -10;
    private double threshold;

    public FlyF(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (this.playerData.getTotalTicks() - 20 < this.lastBypassTick) {
            return;
        }

        if (location.getY() > lastLocation.getY() && lastLocation.isOnGround() && !this.player.getAllowFlight()) {
            double distanceY = location.getY() - lastLocation.getY();
            boolean lastVelocityTicks = this.playerData.getVelocityTicks() <= 4 * this.playerData.getMaxPingTicks();
            if (this.playerData.getVelocityQueue().stream().anyMatch(velocity -> Math.abs(velocity.getY() - distanceY) <= 1.25E-4D)) {
                return;
            }

            if (distanceY < 0.41999998688697815D && (location.getY() - 0.41999998688697815D) % 1.0D > 1.0E-15D) {
                World world = this.player.getWorld();
                Cuboid headCuboid = new Cuboid(lastLocation, location).move(0.0D, 2.0D, 0.0D).expand(0.5D, 0.5D, 0.5D);
                Cuboid groundCuboid = new Cuboid(lastLocation, location).move(0.0D, -0.25D, 0.0D).expand(0.5D, 0.75D, 0.5D);
                int totalTicks = this.playerData.getTotalTicks();
                for (PotionEffect potionEffect : this.player.getActivePotionEffects()) {
                    if (potionEffect.getType() == PotionEffectType.JUMP) {
                        this.lastBypassTick = totalTicks;
                        break;
                    }
                }

                this.run(() -> {
                    if (headCuboid.checkBlocks(world, Material.AIR::equals) && groundCuboid.checkBlocks(world, material -> !BlockUtil.isBadShapeMaterial(material) && !BlockUtil.isBadVelocityMaterial(material))) {
                        for (Entity entity : this.player.getNearbyEntities(2.5D, 2.5D, 2.5D)) {
                            if (entity instanceof Boat || entity instanceof Minecart) {
                                this.threshold = 0.0D;
                                this.decreaseVL(0.025D);
                                this.lastBypassTick = totalTicks - 100;
                                return;
                            }
                        }

                        this.threshold += lastVelocityTicks ? 0.25D : 1.0D;
                        this.handleViolation(String.format("Distance Y: %s Y: %s", distanceY, location.getY()), this.threshold);
                    } else {
                        this.threshold = 0.0D;
                        this.decreaseVL(0.025D);
                        this.lastBypassTick = totalTicks;
                    }
                });
            } else {
                this.threshold = 0.0D;
                this.decreaseVL(0.025D);
            }
        }
    }
}
