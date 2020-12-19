package me.frankthedev.verdict.util;

public enum AnticheatPermissions {

    LOGS_COMMAND("verdict.logs");

    private final String permission;

    AnticheatPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
