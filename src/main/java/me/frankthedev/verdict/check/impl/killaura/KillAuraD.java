package me.frankthedev.verdict.check.impl.killaura;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInUseEntity;

@CheckInfo(name = "Kill Aura D", maxViolation = 8)
public class KillAuraD extends PacketCheck {

    public KillAuraD(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (timestamp - this.playerData.getLastDelayedTimestamp() > 110L && this.playerData.getLastFlyingTimestamp() < 110L) {
            if (aPacket instanceof APacketPlayInUseEntity) {
                APacketPlayInUseEntity packet = (APacketPlayInUseEntity) aPacket;
                if (packet.getAction().isAttack() && this.playerData.isDigging()) {
                    this.handleViolation();
                }
            }
        }
    }
}
