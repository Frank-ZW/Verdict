package me.frankthedev.verdict.data.manager;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.data.PlayerData;
import me.frankthedev.verdict.packet.manager.PacketManager;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PlayerManager {

    private final Verdict plugin;
    private final Map<UUID, PlayerData> players;
    private static PlayerManager instance;

    public PlayerManager(Verdict plugin) {
        this.plugin = plugin;
        this.players = new ConcurrentHashMap<>();
        new Thread(() -> plugin.getServer().getOnlinePlayers().forEach(this::injectPlayer)).start();
    }

    public static void enable(Verdict plugin) {
        PlayerManager.instance = new PlayerManager(plugin);
    }

    public static void disable() {
        Thread disable = new Thread(() -> PlayerManager.instance.plugin.getServer().getOnlinePlayers().forEach(PlayerManager.instance::uninjectPlayer));
        disable.start();
        try {
            disable.join(TimeUnit.SECONDS.toMillis(15L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PlayerManager.instance.players.clear();
        PlayerManager.instance = null;
    }

    public static PlayerManager getInstance() {
        return instance;
    }

    public void injectPlayer(Player player) {
        PlayerData playerData = new PlayerData(player);
        this.players.put(player.getUniqueId(), playerData);
        PacketManager.getInstance().injectPlayerData(playerData);
    }

    public void uninjectPlayer(Player player) {
        PlayerData playerData = this.players.remove(player.getUniqueId());
        if (playerData != null) {
            PacketManager.getInstance().uninjectPlayerData(playerData);
        }
    }

    @Nullable
    public PlayerData getPlayerData(Player player) {
        return this.players.get(player.getUniqueId());
    }

    public Collection<PlayerData> getPlayerDatas() {
        return this.players.values();
    }
}
