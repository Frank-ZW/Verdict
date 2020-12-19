package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayInAbilities extends APacket {

    protected boolean invulnerable;
    protected boolean flying;
    protected boolean allowedFlight;
    protected boolean creative;

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public boolean isAllowedFlight() {
        return this.allowedFlight;
    }

    public boolean isCreative() {
        return this.creative;
    }
}
