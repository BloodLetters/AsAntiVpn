package me.ashesh;

import me.ashesh.Event.joinEvent;
import me.ashesh.utils.ResetQuery;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class AsAntiVpn extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        BukkitTask task = new ResetQuery(this).runTaskLater(this, 1300);
        new joinEvent(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
