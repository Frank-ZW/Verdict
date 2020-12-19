package me.frankthedev.verdict.check.impl.nuker;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.check.CheckInfo;
import me.frankthedev.verdict.check.types.PacketCheck;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.packet.packets.APacketPlayInBlockDig;
import me.frankthedev.verdict.packet.packets.APacketPlayInFlying;

@CheckInfo(name = "Nuker A", maxViolation = 14)
public class NukerA extends PacketCheck {

	private int count = 0;

	public NukerA(Verdict plugin, PlayerData playerData) {
		super(plugin, playerData);
	}

	@Override
	public void handle(APacket aPacket, long timestamp) {
		if (aPacket instanceof APacketPlayInBlockDig) {
			if (++this.count > 6) {
				this.handleViolation();
			}
		} else if (aPacket instanceof APacketPlayInFlying) {
			this.count = 0;
		}
	}
}
