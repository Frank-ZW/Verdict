package me.frankthedev.verdict.check.impl.badpackets;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "Bad Packets A", maxViolation = 1)
public class BadPacketsA extends PacketCheck {

    public BadPacketsA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            APacketPlayInFlying packet = (APacketPlayInFlying) aPacket;
            if (Math.abs(packet.getPitch()) > 90.0F) {
                this.handleViolation();
            }
        }
    }
}
