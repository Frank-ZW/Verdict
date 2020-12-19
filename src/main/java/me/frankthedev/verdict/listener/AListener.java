package me.frankthedev.verdict.listener;

import me.frankthedev.verdict.Verdict;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract class AListener<T extends Event> implements Listener {

    protected Verdict plugin;

    public AListener(Verdict plugin) {
        this.plugin = plugin;
    }

    public abstract void handle(T e);
}
