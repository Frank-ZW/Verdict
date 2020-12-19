package me.frankthedev.verdict.check.impl.timer;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "Timer A", maxViolation = 10)
public class TimerA extends PacketCheck {

    private long lastTimestamp;
    private double balance;

    public TimerA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            if (this.lastTimestamp == 0L) {
                return;
            }

            long rate = timestamp - this.lastTimestamp;
            this.balance += 50.0D;
            this.balance -= rate;
            if (this.balance > 10.0D) {
                this.handleViolation();
                this.balance = 0.0D;
            }
        }
    }
}
