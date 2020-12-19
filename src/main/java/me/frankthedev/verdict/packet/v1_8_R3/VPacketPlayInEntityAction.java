package me.frankthedev.verdict.packet.v1_8_R3;

import me.frankthedev.verdict.packet.packets.APacketPlayInEntityAction;
import me.frankthedev.verdict.util.java.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;

public class VPacketPlayInEntityAction extends APacketPlayInEntityAction {

    @Override
    public void accept(Object packet) {
        if (packet instanceof PacketPlayInEntityAction) {
            this.handle((PacketPlayInEntityAction) packet);
        }
    }

    public void handle(PacketPlayInEntityAction packet) {
        this.entityId = ReflectionUtil.getLocalField(PacketPlayInEntityAction.class, packet, "a");
        switch (packet.b()) {
            case START_SPRINTING:
                this.action = PlayerAction.START_SPRINTING;
                break;
            case STOP_SPRINTING:
                this.action = PlayerAction.STOP_SPRINTING;
                break;
            case START_SNEAKING:
                this.action = PlayerAction.START_SNEAKING;
                break;
            case STOP_SNEAKING:
                this.action = PlayerAction.STOP_SNEAKING;
                break;
            case STOP_SLEEPING:
                this.action = PlayerAction.STOP_SLEEPING;
                break;
            case RIDING_JUMP:
                this.action = PlayerAction.RIDING_JUMP;
                break;
            default:
                this.action = PlayerAction.OPEN_INVENTORY;
                break;
        }
    }
}
