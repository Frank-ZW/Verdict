package me.frankthedev.verdict.command.impl;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.command.ACommand;
import me.frankthedev.verdict.util.AnticheatPermissions;
import me.frankthedev.verdict.util.bukkit.PlayerUtil;
import me.frankthedev.verdict.util.java.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class LogCommand extends ACommand {

    public LogCommand(Verdict plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.PLAYER_ONLY);
            return;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(AnticheatPermissions.LOGS_COMMAND.getPermission())) {
            player.sendMessage(StringUtil.INSUFFICIENT_PERMISSION);
            return;
        }

        if (args.length == 0) {
            player.sendMessage(StringUtil.PREFIX + ChatColor.RED + " To view a player's logs, type \"/verdict logs <player>\"");
        } else {
            Optional<OfflinePlayer> optional = PlayerUtil.getOfflinePlayer(args[0]);
            if (!optional.isPresent()) {
                player.sendMessage(StringUtil.PREFIX + ChatColor.RED + " Failed to find the player specified by name.");
                return;
            }

            OfflinePlayer offlinePlayer = optional.get();

        }
    }
}
