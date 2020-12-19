package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;
import me.frankthedev.verdict.util.bukkit.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;

public abstract class APacketPlayInBlockPlace extends APacket {

    protected BlockPosition position;
    protected ItemStack itemStack;
    protected float cursorX;
    protected float cursorY;
    protected float cursorZ;
    protected int face;
    protected long timestamp;

    public BlockPosition getPosition() {
        return this.position;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public float getCursorX() {
        return this.cursorX;
    }

    public float getCursorY() {
        return this.cursorY;
    }

    public float getCursorZ() {
        return this.cursorZ;
    }

    public int getFace() {
        return this.face;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public boolean isItem() {
        return this.getItemStack() != null;
    }

    public boolean isBlockPlaced() {
        return this.position.getBlockX() != -1 && this.position.getBlockY() != -1 && this.position.getBlockZ() != -1;
    }
}
