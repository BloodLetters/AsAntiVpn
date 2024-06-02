package me.ashesh.Event;

import me.ashesh.AsAntiVpn;
import me.ashesh.utils.ResetQuery;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitTask;

public class joinEvent implements Listener {

    public joinEvent(AsAntiVpn plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        // Logic
    }
}
