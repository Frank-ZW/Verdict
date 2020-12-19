package me.frankthedev.verdict;

import me.frankthedev.verdict.banwave.manager.BanWaveManager;
import me.frankthedev.verdict.command.CommandHandler;
import me.frankthedev.verdict.packet.manager.NMSManager;
import me.frankthedev.verdict.packet.manager.PacketManager;
import me.frankthedev.verdict.listener.AListener;
import me.frankthedev.verdict.listener.bukkit.PlayerJoinListener;
import me.frankthedev.verdict.listener.bukkit.PlayerQuitListener;
import me.frankthedev.verdict.listener.check.PlayerAlertListener;
import me.frankthedev.verdict.listener.check.PlayerBanListener;
import me.frankthedev.verdict.data.manager.PlayerManager;
import me.frankthedev.verdict.util.AnticheatPermissions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public final class Verdict extends JavaPlugin {

    private static Verdict instance;
    private final List<Class<? extends AListener<?>>> listeners = Arrays.asList(PlayerJoinListener.class, PlayerQuitListener.class, PlayerBanListener.class, PlayerAlertListener.class);

    @Override
    public void onEnable() {
        long initial = System.currentTimeMillis();
        instance = this;
        try {
            NMSManager.getInstance();
            PacketManager.getInstance();
        } catch (Throwable t) {
            this.getLogger().log(Level.SEVERE, "Failed to create instance for one or more managers", t);
            this.getServer().shutdown();
        }

        PlayerManager.enable(this);
        BanWaveManager.enable(this);
        this.registerListeners();
        this.registerCommands();
        String startupMessage = String.format("%s" + ChatColor.GREEN + " successfully started up in %sms.", ChatColor.BLUE + "Verdict", System.currentTimeMillis() - initial);
        for (Player player : this.getServer().getOnlinePlayers()) {
            if (player.hasPermission(AnticheatPermissions.LOGS_COMMAND.getPermission())) {
                player.sendMessage(startupMessage);
            }
        }

        this.getLogger().info(startupMessage);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        BanWaveManager.disable();
        PlayerManager.disable();
        instance = null;
    }

    public void registerListeners() {
        for (Class<? extends AListener<?>> clazz : this.listeners) {
            try {
                AListener<?> listener = clazz.asSubclass(AListener.class).getConstructor(Verdict.class).newInstance(this);
                this.getServer().getPluginManager().registerEvents(listener, this);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerCommands() {
        CommandHandler handler = new CommandHandler(this);
        this.getCommand("verdict").setExecutor(handler);
    }

    public static Verdict getInstance() {
        return instance;
    }
}
