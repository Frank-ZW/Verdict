package me.frankthedev.verdict.check.impl.killaura;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;
import me.frankthedev.verdict.packet.packets.APacketPlayInUseEntity;

@CheckInfo(name = "Kill Aura G", maxViolation = 12)
public class KillAuraG extends PacketCheck {

    private int attackTicks;
    private Integer lastTicks;
    private int invalidTicks;
    private int totalTicks;

    public KillAuraG(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            this.attackTicks++;
        } else if (aPacket instanceof APacketPlayInUseEntity) {
            APacketPlayInUseEntity packet = (APacketPlayInUseEntity) aPacket;
            if (packet.getAction().isAttack()) {
                if (this.attackTicks <= 8) {
                    if (this.lastTicks != null) {
                        if (this.lastTicks == this.attackTicks) {
                            this.invalidTicks++;
                        }

                        if (++this.totalTicks >= 25) {
                            if (this.invalidTicks > 22) {
                                this.handleViolation();
                            } else {
                                this.decreaseVL(0.5D);
                            }

                            this.invalidTicks = 0;
                            this.totalTicks = 0;
                        }
                    }

                    this.lastTicks = this.attackTicks;
                }

                this.attackTicks = 0;
            }
        }
    }
}
