package me.frankthedev.verdict.listener.check;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.data.manager.PlayerManager;
import me.frankthedev.verdict.event.PlayerBanEvent;
import me.frankthedev.verdict.listener.AListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class PlayerBanListener extends AListener<PlayerBanEvent> {

    public PlayerBanListener(Verdict plugin) {
        super(plugin);
    }

    @Override
    @EventHandler
    public void handle(PlayerBanEvent e) {
        Player player = e.getPlayer();
        PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);
        if (playerData != null && !e.isCancelled()) {
            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "ban " + player.getName() + " " + e.getCheckName() + "."));
        }
    }
}
