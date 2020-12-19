package me.frankthedev.verdict.check.impl.badpackets;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInEntityAction;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "Bad Packets C", maxViolation = 5)
public class BadPacketsC extends PacketCheck {

    private boolean sent = false;

    public BadPacketsC(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    /**
     * Analyzes the packet order of the player. The Minecraft client can never send
     * multiple PacketPlayInEntityAction packets in a single tick, or every 50ms. If
     * the client sends multiple action packets in a single tick, the player is
     * using a hacked client or an illegitimate modification.
     *
     * @param aPacket       The incoming packet to be analyzed.
     * @param timestamp     The timestamp of when the check was fired.
     */
    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInEntityAction) {
            APacketPlayInEntityAction packet = (APacketPlayInEntityAction) aPacket;
            APacketPlayInEntityAction.PlayerAction action = packet.getAction();
            if (action == APacketPlayInEntityAction.PlayerAction.START_SNEAKING || action == APacketPlayInEntityAction.PlayerAction.STOP_SNEAKING) {
                if (this.sent) {
                    this.handleViolation();
                } else {
                    this.sent = true;
                }
            }
        } else if (aPacket instanceof APacketPlayInFlying) {
            this.sent = false;
        }
    }
}
