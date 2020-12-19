package me.frankthedev.verdict.check.impl.autoclicker;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInArmAnimation;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "AutoClicker B", maxViolation = 14)
public class AutoClickerB extends PacketCheck {

    private int swingTicks = 0;
    private int movements = 0;

    public AutoClickerB(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInArmAnimation && !this.playerData.isDigging() && !this.playerData.hasLag() && !this.playerData.hasFast()) {
            this.swingTicks++;
        } else if (aPacket instanceof APacketPlayInFlying && ++this.movements == 20) {
            if (this.swingTicks > 20) {
                this.handleViolation();
            }

            this.movements = 0;
            this.swingTicks = 0;
        }
    }
}
