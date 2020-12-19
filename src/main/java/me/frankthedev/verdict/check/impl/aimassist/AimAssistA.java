package me.frankthedev.verdict.check.impl.aimassist;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInLook;
import me.frankthedev.verdict.check.CheckInfo;

@CheckInfo(name = "Aim Assist A", maxViolation = 6)
public class AimAssistA extends PacketCheck {

    private Float lastYaw = null;
    private float yawSpeed = 360.0F;

    public AimAssistA(Verdict plugin, PlayerData playerData) {
        super(plugin, playerData);
    }

    @Override
    public void handle(APacket aPacket, long timestamp) {
        if (aPacket instanceof APacketPlayInLook) {
            APacketPlayInLook packet = (APacketPlayInLook) aPacket;
            if (this.lastYaw != null) {
                float deltaYaw = Math.abs(packet.getYaw() - this.lastYaw);
                if (this.yawSpeed < 45.0F) {
                    if (deltaYaw > 345.0F && deltaYaw < 375.0F) {
                        if (Math.abs(Math.abs(360.0f - (deltaYaw + Math.abs(180.0f - Math.abs(packet.getYaw() % 180.0f - this.lastYaw % 180.0f))))) == 0.0) {
                            this.handleViolation(String.format("D: %s", deltaYaw));
                        }
                    } else if (deltaYaw > 172.5F && deltaYaw < 187.5F && Math.abs(Math.abs(180.0f - (deltaYaw + Math.abs(90.0f - Math.abs(packet.getYaw() % 90.0f - this.lastYaw % 90.0f))))) == 0.0) {
                        this.handleViolation(String.format("D: %s", deltaYaw), 0.75D);
                    }

                    this.decreaseVL(0.0025D);
                }

                this.yawSpeed *= 3.0F;
                this.yawSpeed += deltaYaw;
                this.yawSpeed /= 4.0F;
            }

            this.lastYaw = packet.getYaw();
        }
    }
}
