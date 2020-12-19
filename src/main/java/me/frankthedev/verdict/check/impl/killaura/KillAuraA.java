package me.frankthedev.verdict.check.impl.killaura;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInArmAnimation;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;
import me.frankthedev.verdict.packet.packets.APacketPlayInUseEntity;

@CheckInfo(name = "Kill Aura A", maxViolation = 10)
public class KillAuraA extends PacketCheck {

    private boolean sent;

    public KillAuraA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInArmAnimation) {
            this.sent = true;
        } else if (aPacket instanceof APacketPlayInFlying) {
            this.sent = false;
        } else if (aPacket instanceof APacketPlayInUseEntity) {
            APacketPlayInUseEntity packet = (APacketPlayInUseEntity) aPacket;
            if (packet.getAction().isAttack()) {
                if (!this.sent) {
                    this.handleViolation();
                } else {
                    this.sent = false;
                }
            }
        }
    }
}
