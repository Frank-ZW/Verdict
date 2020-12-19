package me.frankthedev.verdict.check.impl.phase;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInEntityAction;
import me.frankthedev.verdict.packet.packets.APacketPlayInPosition;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "Phase A", maxViolation = 20)
public class PhaseA extends PacketCheck {

    private int stage;

    public PhaseA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            if (aPacket instanceof APacketPlayInPosition) {
                if (this.stage >= 2) {
                    this.stage++;
                    return;
                }
            }

            if (this.stage == 0) {
                this.stage++;
                return;
            }

            this.stage = 0;
        } else if (aPacket instanceof APacketPlayInEntityAction) {
            APacketPlayInEntityAction packet = (APacketPlayInEntityAction) aPacket;
            APacketPlayInEntityAction.PlayerAction action = packet.getAction();
            if (action.getType().equals(APacketPlayInEntityAction.PlayerAction.PlayerActionType.SNEAKING)) {
                if (action.isValue()) {
                    if (this.stage >= 3) {
                        this.handleViolation();
                        return;
                    }

                    if (this.stage == 1) {
                        this.stage++;
                    } else {
                        this.stage = 0;
                    }
                }
            }
        }
    }
}
