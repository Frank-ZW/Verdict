package me.frankthedev.verdict.command.impl;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.banwave.manager.BanWaveManager;
import me.frankthedev.verdict.command.ACommand;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.data.manager.PlayerManager;
import me.frankthedev.verdict.event.BanWaveStartEvent;
import me.frankthedev.verdict.event.PlayerBanWaveEvent;
import me.frankthedev.verdict.util.java.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerdictBanWaveCommand extends ACommand {

    public VerdictBanWaveCommand(Verdict plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            String subargument = args[0].toLowerCase();
            if (subargument.equalsIgnoreCase("add")) {
                if (args.length != 2) {
                    sender.sendMessage(StringUtil.PREFIX + ChatColor.RED + " To manually add someone to the ban wave, type \"/verdict add <player>\".");
                    return;
                }

                Player player = this.plugin.getServer().getPlayerExact(args[1]);
                if (player == null) {
                    sender.sendMessage(StringUtil.PREFIX + ChatColor.RED + " This player does not exist.");
                    return;
                }

                PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);
                if (playerData == null) {
                    sender.sendMessage(StringUtil.PREFIX + ChatColor.RED + " This player does not have an associated player data.");
                    return;
                }

                if (playerData.isInBanWave()) {
                    sender.sendMessage(StringUtil.PREFIX + ChatColor.GREEN + " This player is already in the ban wave.");
                } else {
                    playerData.setInBanWave(true);
                    PlayerBanWaveEvent banWaveEvent = new PlayerBanWaveEvent(player, "Manual");
                    this.plugin.getServer().getPluginManager().callEvent(banWaveEvent);
                    sender.sendMessage(StringUtil.PREFIX + ChatColor.GREEN + " Added " + ChatColor.WHITE + player.getName() + ChatColor.GREEN + " to the ban wave.");
                }
            } else if (subargument.equalsIgnoreCase("list")) {

            } else if (subargument.equalsIgnoreCase("stop")) {
                BanWaveManager.getInstance().setStarted(false);
                sender.sendMessage(StringUtil.PREFIX + ChatColor.RED + " Cancelled any ongoing ban waves.");
            } else if (subargument.equalsIgnoreCase("start")) {
                BanWaveManager.getInstance().setStarted(true);
                BanWaveStartEvent banWaveStartEvent = new BanWaveStartEvent(sender instanceof Player ? sender.getName() : "Console");
                this.plugin.getServer().getPluginManager().callEvent(banWaveStartEvent);
            }
        }
    }
}
