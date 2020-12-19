package me.frankthedev.verdict.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BanWaveStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final String instigator;

    public BanWaveStartEvent(String instigator) {
        super(true);
        this.instigator = instigator;
    }

    public String getInstigator() {
        return this.instigator;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
