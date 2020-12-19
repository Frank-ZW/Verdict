package me.frankthedev.verdict.check.impl.pingspoof;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInKeepAlive;

@CheckInfo(name = "Ping Spoof A", maxViolation = 12)
public class PingSpoofA extends PacketCheck {

    private Integer lastId = null;

    public PingSpoofA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInKeepAlive) {
            APacketPlayInKeepAlive packet = (APacketPlayInKeepAlive) aPacket;
            int id = packet.getId();
            if (this.lastId != null && this.lastId > id && this.playerData.getTotalTicks() > 100) {
                this.handleViolation();
            }

            this.lastId = id;
        }
    }
}
