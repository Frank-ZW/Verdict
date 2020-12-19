package me.frankthedev.verdict.check.impl.killaura;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInBlockPlace;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;
import me.frankthedev.verdict.packet.packets.APacketPlayInUseEntity;

@CheckInfo(name = "Kill Aura C", maxViolation = 8)
public class KillAuraC extends PacketCheck {

    private boolean sent;

    public KillAuraC(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            this.sent = false;
        } else if (aPacket instanceof APacketPlayInBlockPlace) {
            this.sent = true;
        } else if (aPacket instanceof APacketPlayInUseEntity) {
            APacketPlayInUseEntity packet = (APacketPlayInUseEntity) aPacket;
            if (packet.getAction().isAttack() && this.sent) {
                this.handleViolation();
            }
        }
    }
}
