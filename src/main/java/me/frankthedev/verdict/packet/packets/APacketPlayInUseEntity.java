package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public abstract class APacketPlayInUseEntity extends APacket {

    protected int entityID;
    protected EntityUseAction action;
    protected double blockX;
    protected double blockY;
    protected double blockZ;

    public enum EntityUseAction {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        EntityUseAction() {}

        public boolean isAttack() {
            return this.equals(EntityUseAction.ATTACK);
        }

        public boolean isInteract() {
            return this.equals(EntityUseAction.INTERACT);
        }

        public boolean isInteractAt() {
            return this.equals(EntityUseAction.INTERACT_AT);
        }
    }

    public int getEntityID() {
        return this.entityID;
    }

    public EntityUseAction getAction() {
        return this.action;
    }

    public double getBlockX() {
        return this.blockX;
    }

    public double getBlockY() {
        return this.blockY;
    }

    public double getBlockZ() {
        return this.blockZ;
    }

    public abstract Entity getEntity(World world);
}
