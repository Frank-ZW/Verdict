package me.frankthedev.verdict.util.wrapper;

import java.util.UUID;

public class PlayerWrapper {

    private final UUID uniqueId;
    private final String name;
    private final double totalViolations;

    public PlayerWrapper(UUID uniqueId, String name, double totalViolations) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.totalViolations = totalViolations;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public String getName() {
        return this.name;
    }

    public double getTotalViolations() {
        return this.totalViolations;
    }
}
