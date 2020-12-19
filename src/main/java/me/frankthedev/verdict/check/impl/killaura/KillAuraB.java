package me.frankthedev.verdict.check.impl.killaura;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;
import me.frankthedev.verdict.util.MathUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@CheckInfo(name = "Kill Aura B", maxViolation = 12)
public class KillAuraB extends PacketCheck {

    private final Queue<Float> pitches = new ConcurrentLinkedDeque<>();
    private Float lastPitch = null;

    public KillAuraB(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            APacketPlayInFlying packet = (APacketPlayInFlying) aPacket;
            if (this.lastPitch != null && this.playerData.getLastAttackTicks() <= 5 && this.playerData.getLastAttacked() != null) {
                this.pitches.add(Math.abs(packet.getPitch() - this.lastPitch));
                if (this.pitches.size() > 20) {
                    double deviation = MathUtil.getStandardDeviation(this.pitches);
                    double average = MathUtil.getAverage(this.pitches);
                    if ((average > 17.5D && deviation > 15.0D) || (average > 22.5D && deviation > 12.5D)) {
                        this.handleViolation(String.format("Avg: %s Dev: %s", average, deviation));
                    } else {
                        this.decreaseVL(0.025);
                    }

                    this.pitches.clear();
                }
            }

            this.lastPitch = packet.getPitch();
        }
    }
}
