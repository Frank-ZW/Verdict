package me.frankthedev.verdict.check.impl.fly;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInAbilities;

@CheckInfo(name = "Fly H", maxViolation = 12)
public class FlyH extends PacketCheck {

    public FlyH(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInAbilities) {
            APacketPlayInAbilities packet = (APacketPlayInAbilities) aPacket;
            if (packet.isAllowedFlight() && (!this.playerData.canFly() || !this.player.getAllowFlight())) {
                this.handleViolation();
            }
        }
    }
}
