package me.frankthedev.verdict.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAlertEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final String checkName;
    private final int violations;
    private final int ping;
    private boolean cancelled;

    public PlayerAlertEvent(Player player, String checkName, int violations, int ping) {
        super(true);
        this.player = player;
        this.checkName = checkName;
        this.violations = violations;
        this.ping = ping;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCheckName() {
        return checkName;
    }

    public int getViolations() {
        return violations;
    }

    public int getPing() {
        return ping;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
