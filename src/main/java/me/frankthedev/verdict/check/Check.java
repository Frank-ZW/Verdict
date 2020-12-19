package me.frankthedev.verdict.check;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.log.manager.AlertManager;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Check {

    protected final ExecutorService executorService;
    protected final Verdict plugin;
    protected final Player player;
    protected final PlayerData playerData;
    protected double violations;
    private String name;
    private int lastViolation;
    private int maxViolation;

    public Check(Verdict plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        this.violations = 0.0D;
        this.lastViolation = 0;
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            CheckInfo annotation = this.getClass().getAnnotation(CheckInfo.class);
            this.setName(annotation.name());
            this.setMaxViolation(annotation.maxViolation());
        }

        this.executorService = Executors.newFixedThreadPool(3);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public double getViolations() {
        return this.violations;
    }

    public void setViolations(double violations) {
        this.violations = violations;
    }

    public int getLastViolation() {
        return this.lastViolation;
    }

    public void setLastViolation(int lastViolation) {
        this.lastViolation = lastViolation;
    }

    public int getMaxViolation() {
        return this.maxViolation;
    }

    public void setMaxViolation(int maxViolation) {
        this.maxViolation = maxViolation;
    }

    public void decreaseVL(double amount) {
        this.violations -= Math.min(this.violations, amount);
    }

    public void run(Runnable runnable) {
        this.executorService.execute(runnable);
    }

    public void handleViolation() {
        this.handleViolation("");
    }

    public void handleViolation(String data) {
        this.handleViolation(data, 1.0D);
    }

    public void handleViolation(String data, double incrementAmount) {
        AlertManager.getInstance().handleViolation(this.playerData, this, data, incrementAmount);
    }
}
