package me.frankthedev.verdict.packet.packets;

import me.frankthedev.verdict.packet.APacket;

public abstract class APacketPlayInEntityAction extends APacket {

    protected int entityId;
    protected PlayerAction action;

    public int getEntityId() {
        return this.entityId;
    }

    public PlayerAction getAction() {
        return this.action;
    }

    public enum PlayerAction {
        START_SPRINTING(PlayerActionType.SPRINTING, true),
        STOP_SPRINTING(PlayerActionType.SPRINTING, false),
        START_SNEAKING(PlayerActionType.SNEAKING, true),
        STOP_SNEAKING(PlayerActionType.SNEAKING, false),
        FALL_FLYING(PlayerActionType.FALL_FLYING, true),
        STOP_SLEEPING(PlayerActionType.OTHER, false),
        RIDING_JUMP(PlayerActionType.OTHER, true),
        OPEN_INVENTORY(PlayerActionType.OTHER, true);

        private final PlayerActionType type;
        private final boolean value;

        PlayerAction(PlayerActionType type, boolean value) {
            this.type = type;
            this.value = value;
        }

        public PlayerActionType getType() {
            return type;
        }

        public boolean isValue() {
            return value;
        }

        public enum PlayerActionType {
            SPRINTING,
            SNEAKING,
            FALL_FLYING,
            OTHER;

            PlayerActionType() {}
        }
    }
}
