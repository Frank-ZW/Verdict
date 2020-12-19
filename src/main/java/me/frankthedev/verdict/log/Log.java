package me.frankthedev.verdict.log;

import java.util.UUID;

public class Log {

    private final long timestamp = System.currentTimeMillis();
    private final UUID playerUUID;
    private final String checkName;
    private final int ping;
    private final String data;

    public Log(UUID playerUUID, String checkName, int ping, String data) {
        this.playerUUID = playerUUID;
        this.checkName = checkName;
        this.ping = ping;
        this.data = data;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public String getCheckName() {
        return this.checkName;
    }

    public int getPing() {
        return this.ping;
    }

    public String getData() {
        return this.data;
    }
}
