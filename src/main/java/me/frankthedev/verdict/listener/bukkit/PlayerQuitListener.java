package me.frankthedev.verdict.listener.bukkit;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.manager.PlayerManager;
import me.frankthedev.verdict.listener.AListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerQuitListener extends AListener<PlayerQuitEvent> {

    public PlayerQuitListener(Verdict plugin) {
        super(plugin);
    }

    @Override
    @EventHandler
    public void handle(PlayerQuitEvent e) {
        new Thread(() -> PlayerManager.getInstance().uninjectPlayer(e.getPlayer())).start();
    }
}
