package me.frankthedev.verdict.listener.check;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.data.manager.PlayerManager;
import me.frankthedev.verdict.event.PlayerAlertEvent;
import me.frankthedev.verdict.util.java.StringUtil;
import me.frankthedev.verdict.listener.AListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class PlayerAlertListener extends AListener<PlayerAlertEvent> {

    public PlayerAlertListener(Verdict plugin) {
        super(plugin);
    }

    @Override
    @EventHandler
    public void handle(PlayerAlertEvent e) {
        String alertMessage = String.format("%s" + ChatColor.DARK_GRAY + " [" + ChatColor.RED + "%s" + ChatColor.DARK_GRAY + "] [" + ChatColor.YELLOW + "%s ms" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + " is suspected of using " + ChatColor.RED + "%s" + ChatColor.DARK_GRAY + " [" + ChatColor.RED + "%s" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + ".", StringUtil.PREFIX, e.getPlayer().getName(), e.getPing(), e.getCheckName(), e.getViolations());
        PlayerManager.getInstance().getPlayerDatas().stream().filter(PlayerData::isReceiveAlerts).map(PlayerData::getPlayer).forEach(player -> player.sendMessage(alertMessage));
        this.plugin.getLogger().info(alertMessage);
    }
}
