package me.frankthedev.verdict.check.impl.killaura;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInUseEntity;
import org.bukkit.entity.Entity;

@CheckInfo(name = "Kill Aura E", maxViolation = 8)
public class KillAuraE extends PacketCheck {

    public KillAuraE(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInUseEntity) {
            APacketPlayInUseEntity packet = (APacketPlayInUseEntity) aPacket;
            if (packet.getAction().isAttack()) {
                Entity targetEntity = packet.getEntity(player.getWorld());
                if (targetEntity != null && !player.hasLineOfSight(targetEntity)) {
                    this.handleViolation();
                } else {
                    this.decreaseVL(0.5D);
                }
            }
        }
    }
}
