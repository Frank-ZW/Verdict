package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;

public abstract class APacketPlayInBlockDig extends APacket {

    protected BlockPosition position;
    protected EnumDirection face;
    protected PlayerDigType type;

    public BlockPosition getPosition() {
        return this.position;
    }

    public EnumDirection getFace() {
        return this.face;
    }

    public PlayerDigType getType() {
        return this.type;
    }

    public enum PlayerDigType {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM;

        PlayerDigType() {}
    }
}
