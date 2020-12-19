package me.frankthedev.verdict.banwave;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.banwave.manager.BanWaveManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class BanWaveRunnable extends BukkitRunnable {

    private final Verdict plugin;
    private int countdown = 5;

    public BanWaveRunnable(Verdict plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        BanWaveManager manager = BanWaveManager.getInstance();
        if (manager.isStarted()) {
            if (--this.countdown > 0) {
                this.plugin.getServer().broadcastMessage(ChatColor.RED + "The ban wave will commence in " + ChatColor.WHITE + this.countdown + ChatColor.GREEN + " second(s)...");
            } else if (this.countdown == 0) {
                this.plugin.getServer().broadcastMessage(ChatColor.GREEN + "The ban wave has commenced.");
                this.countdown = -1;
            } else if (manager.getCheaterNames().size() > 0) {

            } else if (manager.getCheaterNames().size() == 0) {
                manager.setStarted(false);
            }
        } else {
            this.plugin.getServer().broadcastMessage(ChatColor.GOLD + "--------------------------------------------------\n" + ChatColor.RED + "Verdict Anticheat has finished the ban wave. A total of " + manager.getCheaterNames().size() + " players were banned.\n--------------------------------------------------\n");
            manager.clear();
            this.cancel();
        }
    }
}
