package me.frankthedev.verdict.banwave.manager;

import me.frankthedev.verdict.Verdict;
import me.frankthedev.verdict.util.wrapper.PlayerWrapper;

import java.util.*;

public class BanWaveManager {

    private final Verdict plugin;
    private final Deque<UUID> cheaters = new LinkedList<>();
    private final Map<UUID, String> cheaterNames = new HashMap<>();
    private boolean started;
    private static BanWaveManager instance;

    public BanWaveManager(Verdict plugin) {
        this.plugin = plugin;
    }

    public static void enable(Verdict plugin) {
        BanWaveManager.instance = new BanWaveManager(plugin);
    }

    public static void disable() {
        BanWaveManager.instance = null;
    }

    public static BanWaveManager getInstance() {
        return instance;
    }

    public void loadCheaters() {
        List<UUID> cheaters = new LinkedList<>();
        //List<PlayerWrapper> playerWrappers = this.plugin.getEmbeddedDatabase().loadPlayerWrappers();
//        for (PlayerWrapper wrapper : playerWrappers) {
//            if (wrapper.getTotalViolations() >= 50D) {
//                this.cheaterNames.put(wrapper.getUniqueId(), wrapper.getName());
//                cheaters.add(wrapper.getUniqueId());
//            }
//        }

        cheaters.sort((uuid1, uuid2) -> {
            String name1 = this.cheaterNames.get(uuid1);
            String name2 = this.cheaterNames.get(uuid2);
            return name1.compareToIgnoreCase(name2);
        });
        this.cheaters.addAll(cheaters);
    }

    public Deque<UUID> getCheaters() {
        return this.cheaters;
    }

    public Map<UUID, String> getCheaterNames() {
        return this.cheaterNames;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void clear() {
        this.cheaters.clear();
        this.cheaterNames.clear();
    }
}
