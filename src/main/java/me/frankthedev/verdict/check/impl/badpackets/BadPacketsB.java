package me.frankthedev.verdict.check.impl.badpackets;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "Bad Packets B", maxViolation = 1)
public class BadPacketsB extends PacketCheck {

    public BadPacketsB(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying && this.playerData.getPackets() > 22) {
            this.handleViolation(String.format("Packets: %s", this.playerData.getPackets()));
        }
    }
}
