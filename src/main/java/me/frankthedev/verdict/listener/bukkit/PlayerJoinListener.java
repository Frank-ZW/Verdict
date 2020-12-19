package me.frankthedev.verdict.listener.bukkit;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.manager.PlayerManager;
import me.frankthedev.verdict.listener.AListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends AListener<PlayerJoinEvent> {

    public PlayerJoinListener(Verdict plugin) {
        super(plugin);
    }

    @Override
    @EventHandler
    public void handle(PlayerJoinEvent e) {
        new Thread(() -> PlayerManager.getInstance().injectPlayer(e.getPlayer())).start();
    }
}
