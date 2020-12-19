package me.frankthedev.verdict.check.types;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.check.Check;

public abstract class PacketCheck extends Check {

    public PacketCheck(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    public abstract void handle(APacket aPacket, long timestamp);
}
