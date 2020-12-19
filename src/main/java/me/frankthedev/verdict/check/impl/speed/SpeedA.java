package me.frankthedev.verdict.check.impl.speed;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.manager.NMSManager;
import me.frankthedev.verdict.util.bukkit.MutableBlockLocation;
import me.frankthedev.verdict.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(name = "Speed A", maxViolation = 24)
public class SpeedA extends PositionCheck {

    private int lastCheckTick;
    private boolean inLadder;
    private double lastDifference;
    private int threshold;

    public SpeedA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (location.getY() > lastLocation.getY() && !lastLocation.isOnGround() && !location.isOnGround()) {
            if (!this.inLadder) {
                int lastCheckTick = this.lastCheckTick;
                this.lastCheckTick++;
                if (lastCheckTick < 10) {
                    return;
                }

                this.lastCheckTick = 0;
                PlayerLocation current = location.cloneLocation();
                World world = this.player.getWorld();
                this.run(() -> {
                    MutableBlockLocation blockLocation = current.getMutableBlockLocation();
                    Material blockType = NMSManager.getInstance().getType(world, blockLocation);
                    if (blockType == Material.LADDER || blockType == Material.VINE) {
                        this.inLadder = true;
                    }
                });

                return;
            }

            double distanceY = location.getY() - lastLocation.getY();
            if (distanceY > 0.118D) {
                int threshold = this.threshold;
                this.threshold++;
                if (threshold > 1 && distanceY >= this.lastDifference * 0.95D) {
                    this.threshold = 0;
                    PlayerLocation current = location.cloneLocation();
                    World world = this.player.getWorld();
                    this.run(() -> {
                        MutableBlockLocation blockLocation = current.getMutableBlockLocation();
                        Material blockType = NMSManager.getInstance().getType(world, blockLocation);
                        if (blockType == Material.LADDER || blockType == Material.VINE) {
                            this.handleViolation(String.format("Distance: %s", distanceY));
                        } else {
                            this.inLadder = false;
                        }
                    });
                }
            } else {
                this.threshold = 0;
            }

            this.lastDifference = distanceY;
            return;
        }

        this.inLadder = false;
    }
}
