package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PositionCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.util.location.PlayerLocation;

@CheckInfo(name = "Fly G", maxViolation = 28)
public class FlyG extends PositionCheck {

    public FlyG(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation lastLocation, long timestamp) {
        if (!this.playerData.canFly() && !location.isOnGround() && !lastLocation.isOnGround()) {
            float sinYaw = (float) Math.sin(Math.toRadians(lastLocation.getYaw()));
            float cosYaw = (float) Math.cos(Math.toRadians(lastLocation.getYaw()));
            double velX = location.getX() - lastLocation.getX();
            double velZ = location.getZ() - lastLocation.getZ();
            double lastVelX = lastLocation.getX() - this.playerData.getLastLastLocation().getX();
            double lastVelZ = lastLocation.getZ() - this.playerData.getLastLastLocation().getZ();

            int strafe = (int) Math.round(cosYaw * (lastVelX - velX) + sinYaw * (lastVelZ - velZ));
            int forward = (int) Math.round(sinYaw * (velX - lastVelX) - cosYaw * (velZ - lastVelZ));

            if (strafe == 0 && forward == 0) {
                this.decreaseVL(0.05D);
            } else {
                if(strafe != 0) {
                    this.handleViolation();
                }

                if (forward != 0) {
                    this.handleViolation();
                }
            }
        }
    }
}
