package me.frankthedev.verdict.command;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.command.impl.LogCommand;
import me.frankthedev.verdict.command.impl.VerdictBanWaveCommand;
import me.frankthedev.verdict.util.java.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {

    private final Verdict plugin;
    private final Map<String, ACommand> commands;

    public CommandHandler(Verdict plugin) {
        this.plugin = plugin;
        this.commands = new HashMap<>();

        this.register("banwave", new VerdictBanWaveCommand(plugin));
        this.register("logs", new LogCommand(plugin));
    }

    public void register(String name, ACommand command) {
        this.commands.put(name, command);
    }

    public ACommand getExecutor(String name) {
        return this.commands.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("TEST");
            return true;
        }

        if (args[0].equalsIgnoreCase("banwave")) {
            this.getExecutor("banwave").onCommand(sender, args);
            return true;
        }

        sender.sendMessage(StringUtil.PREFIX + ChatColor.RED + " This command does not exist. Type \"/verdict\" to bring up the help menu.");
        return true;
    }
}
