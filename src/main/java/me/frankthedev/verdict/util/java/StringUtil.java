package me.frankthedev.verdict.util.java;

import org.bukkit.ChatColor;

public class StringUtil {

    public static final String PREFIX;
    public static final String PLAYER_ONLY;
    public static final String INSUFFICIENT_PERMISSION;
    public static final String SUBSCRIBED_ALERTS;
    public static final String UNSUBSCRIBE_ALERTS;
    public static final String INVALID_COMMAND;

    static {
        PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Verdict" + ChatColor.DARK_GRAY + "]";
        PLAYER_ONLY = PREFIX + ChatColor.GRAY + " You must be a player to run this command.";
        INSUFFICIENT_PERMISSION = PREFIX + ChatColor.GRAY + " You do not have permission to run this command.";
        SUBSCRIBED_ALERTS = PREFIX + ChatColor.GRAY + " You have subscribed to Verdict alerts.";
        UNSUBSCRIBE_ALERTS = PREFIX + ChatColor.GRAY + " You have unsubscribed to Verdict alerts.";
        INVALID_COMMAND = PREFIX + ChatColor.GRAY + " This command does not exist.";
    }
}
