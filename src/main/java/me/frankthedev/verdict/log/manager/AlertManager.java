package me.frankthedev.verdict.log.manager;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.log.Log;
import me.frankthedev.verdict.event.PlayerAlertEvent;
import me.frankthedev.verdict.check.Check;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.event.PlayerBanEvent;

public class AlertManager {

    private final Verdict plugin;
    private static AlertManager instance;

    public AlertManager() {
        this.plugin = Verdict.getInstance();
    }

//    public static void enable(Verdict plugin) {
//        AlertManager.instance = new AlertManager(plugin);
//    }
//
//    public static void disable() {
//        AlertManager.instance = null;
//    }

    public static AlertManager getInstance() {
        return AlertManager.instance == null ? AlertManager.instance = new AlertManager() : AlertManager.instance;
    }

    public boolean handleAlert(PlayerData playerData, Check check, int violations) {
        PlayerAlertEvent event = new PlayerAlertEvent(playerData.getPlayer(), check.getName(), violations, playerData.getAveragePing());
        this.plugin.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public void handleBan(PlayerData playerData, Check check) {
        PlayerBanEvent event = new PlayerBanEvent(playerData.getPlayer(), check.getName());
        this.plugin.getServer().getPluginManager().callEvent(event);
    }

    public void handleViolation(PlayerData playerData, Check check, String data, double incrementAmount) {
        int violations = (int) Math.floor(check.getViolations());
        check.setViolations(check.getViolations() + Math.min(playerData.hasLag() ? 3.0D : 5.0D, incrementAmount));
        if (violations > check.getLastViolation()) {
            if (this.handleAlert(playerData, check, violations)) {
                Log log = new Log(playerData.getUniqueId(), check.getName(), playerData.getAveragePing(), data);
                playerData.addLog(log);
            }

            if (check.getViolations() >= (double) check.getMaxViolation()) {
//                this.handleBan(playerData, check);
            }
        }

        check.setLastViolation(violations);
    }
}
