package me.frankthedev.verdict.check.impl.autoclicker;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInArmAnimation;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "AutoClicker A", maxViolation = 20)
public class AutoClickerA extends PacketCheck {

    private int swingTicks;
    private double avgSpeed;
    private double avgDeviation;

    public AutoClickerA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInArmAnimation) {
            if (this.playerData.isDigging() || this.swingTicks > 5) {
                return;
            }

            double speed = this.swingTicks * 50.0D;
            this.avgSpeed = (this.avgSpeed * 14.0D + speed) / 15.0D;
            this.playerData.setCps((int) (1000.0D / this.avgSpeed));
            double deviation = Math.abs(speed - this.avgSpeed);
            this.avgDeviation = (this.avgDeviation * 9.0D + deviation) / 10.0D;
            if (this.avgDeviation < 3) {
                this.handleViolation(String.format("Deviation: %.3f", this.avgDeviation));
            } else {
                this.decreaseVL(0.5D);
            }

            this.swingTicks = 0;
        } else if (aPacket instanceof APacketPlayInFlying) {
            this.swingTicks++;
        }
    }
}
