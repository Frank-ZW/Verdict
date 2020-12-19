package me.frankthedev.verdict.check.impl.nofall;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.Cuboid;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(name = "No Fall A", maxViolation = 22)
public class NoFallA extends PositionCheck {

    public NoFallA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        World world = player.getWorld();
        Cuboid cuboid = new Cuboid(location).add(-1.0D, -1.0D, -1.0D, 1.0D, 0.0D, 1.0D);
        boolean inAir = cuboid.checkBlocks(world, Material.AIR::equals);
        if (location.isOnGround() && inAir) {
            this.handleViolation();
        } else {
            this.decreaseVL(0.1D);
        }
    }
}
