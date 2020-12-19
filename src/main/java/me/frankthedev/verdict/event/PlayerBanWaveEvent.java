package me.frankthedev.verdict.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBanWaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final String reason;

    public PlayerBanWaveEvent(Player player, String reason) {
        super(true);
        this.player = player;
        this.reason = reason;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getReason() {
        return this.reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
