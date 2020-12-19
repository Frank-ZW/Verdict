package me.frankthedev.verdict.check.impl.servercrasher;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInCustomPayload;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "Server Crasher A", maxViolation = 8)
public class ServerCrasherA extends PacketCheck {

    private int threshold;

    public ServerCrasherA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            this.threshold -= Math.min(this.threshold, 1);
        } else if (aPacket instanceof APacketPlayInCustomPayload) {
            APacketPlayInCustomPayload packet = (APacketPlayInCustomPayload) aPacket;
        }
    }
}
