package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.bukkit.BlockUtil;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Location;

@CheckInfo(name = "Fly E", maxViolation = 20)
public class FlyE extends PositionCheck {

    private double lastDistY;
    private boolean lastOnGround;
    private boolean lastLastOnGround;

    public FlyE(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (!player.getAllowFlight() && !this.playerData.isUnderBlock()) {
            double distY = location.getY() - lastLocation.getY();
            double lastDistY = this.lastDistY;
            this.lastDistY = distY;

            double predictedDist = (lastDistY - 0.08D) * 0.9800000190734863D;
            Location bukkitLocation = location.toBukkitLocation(player.getWorld());
            boolean onGround = BlockUtil.isOnGround(bukkitLocation, 0) || BlockUtil.isOnGround(bukkitLocation, 1);
            boolean lastOnGround = this.lastOnGround;
            boolean lastLastOnGround = this.lastLastOnGround;

            this.lastLastOnGround = this.lastOnGround;
            this.lastOnGround = onGround;
            if (!onGround && !lastOnGround && !lastLastOnGround && this.playerData.getLastAttackTicks() > 10 && Math.abs(predictedDist) >= 0.005D) {
                if (Math.abs(distY - predictedDist) >= 0.001D) {
                    this.handleViolation(String.format("Predicted: %.3f. Distance: %.3f", predictedDist, distY));
                } else {
                    this.decreaseVL(0.5D);
                }
            } else {
                this.decreaseVL(0.25D);
            }
        }
    }
}
