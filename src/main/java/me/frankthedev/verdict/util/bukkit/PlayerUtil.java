package me.frankthedev.verdict.util.bukkit;

import me.frankthedev.verdict.Verdict;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.Optional;

public class PlayerUtil {

    public static Optional<OfflinePlayer> getOfflinePlayer(String name) {
        return Arrays.stream(Verdict.getInstance().getServer().getOfflinePlayers()).filter(player -> name.equalsIgnoreCase(player.getName())).findFirst();
    }
}
