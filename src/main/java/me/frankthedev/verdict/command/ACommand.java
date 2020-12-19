package me.frankthedev.verdict.command;

import me.frankthedev.verdict.Verdict;
import org.bukkit.command.CommandSender;

public abstract class ACommand {

    protected final Verdict plugin;

    public ACommand(Verdict plugin) {
        this.plugin = plugin;
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
