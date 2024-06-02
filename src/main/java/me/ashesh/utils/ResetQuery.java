package me.ashesh.utils;

import me.ashesh.Manager.Algorithm;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ResetQuery extends BukkitRunnable {
    private final JavaPlugin plugin;

    public ResetQuery(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Algorithm.getIpintel = 0;
        Algorithm.IPAPI = 0;
    }

}
