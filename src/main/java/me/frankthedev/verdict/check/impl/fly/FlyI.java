package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.Cuboid;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Material;

@CheckInfo(name = "Fly I", maxViolation = 20)
public class FlyI extends PositionCheck {

    public FlyI(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (!this.player.isFlying() && this.playerData.getTeleportTicks() > 1) {
            double distanceY = lastLocation.getY() - location.getY();
            Cuboid cuboid = new Cuboid(location).expand(0.3D, 0.3D, 0.3D);
            this.run(() -> {
                if (distanceY > 0.0D && distanceY < 6.25E-4D && !cuboid.checkBlocks(this.player.getWorld(), Material.AIR::equals)) {
                    this.handleViolation();
                } else {
                    this.decreaseVL(0.25D);
                }
            });
        }
    }
}
