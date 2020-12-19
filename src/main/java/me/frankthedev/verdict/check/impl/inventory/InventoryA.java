package me.frankthedev.verdict.check.impl.inventory;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInArmAnimation;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;
import me.frankthedev.verdict.packet.packets.APacketPlayInWindowClick;

@CheckInfo(name = "Inventory A", maxViolation = 1)
public class InventoryA extends PacketCheck {

    private boolean click = false;
    private boolean swing = false;

    public InventoryA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInFlying) {
            if (this.swing && this.click) {
                this.handleViolation();
            }

            this.swing = false;
            this.click = false;
        } else if (aPacket instanceof APacketPlayInArmAnimation) {
            this.swing = true;
        } else if (aPacket instanceof APacketPlayInWindowClick) {
            this.click = true;
        }
    }
}
