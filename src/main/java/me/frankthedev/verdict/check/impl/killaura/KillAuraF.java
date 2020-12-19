package me.frankthedev.verdict.check.impl.killaura;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.Cuboid;
import me.frankthedev.verdict.util.bukkit.BlockUtil;
import me.frankthedev.verdict.util.MathUtil;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;

@CheckInfo(name = "Kill Aura F", maxViolation = 18)
public class KillAuraF extends PositionCheck {

    private int threshold = 0;
    private Double lastOffset = null;

    public KillAuraF(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (this.playerData.getLastAttackTicks() <= 2 && this.playerData.isSprinting() && location.isOnGround() && lastLocation.isOnGround()) {
            double yaw = Math.toDegrees(-Math.atan2(lastLocation.getX() - location.getX(), lastLocation.getZ() - location.getZ()));
            double yawOffset = Math.min(MathUtil.getDistanceBetweenAngles(yaw, lastLocation.getYaw()), MathUtil.getDistanceBetweenAngles(yaw, location.getYaw()));
            if (this.lastOffset != null) {
                double diffAngle = MathUtil.getDistanceBetweenAngles(this.lastOffset, yawOffset);
                if (yawOffset > 47.5D) {
                    if (diffAngle < 5.0D && ++this.threshold > 5) {
                        World world = this.player.getWorld();
                        Cuboid cuboid = new Cuboid(location).expand(0.5D, 0.5D, 0.5D);
                        this.run(() -> {
                            if (cuboid.checkBlocks(world, material -> !BlockUtil.isBadVelocityMaterial(material))) {
                                this.handleViolation(String.format("Yaw: %s Offset: %s", yaw, yawOffset));
                            } else {
                                this.threshold = -20;
                            }
                        });

                        this.threshold = 3;
                    }
                } else {
                    this.threshold = 0;
                }
            }

            this.lastOffset = yawOffset;
        } else {
            this.lastOffset = null;
            this.threshold = 0;
        }
    }
}
