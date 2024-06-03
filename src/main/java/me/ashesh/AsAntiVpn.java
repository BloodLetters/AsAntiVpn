package me.ashesh;

import me.ashesh.Command.TabCompleter;
import me.ashesh.Event.joinEvent;
import me.ashesh.utils.ResetQuery;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class AsAntiVpn extends JavaPlugin {

    private static AsAntiVpn instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        BukkitTask task = new ResetQuery(this).runTaskLater(this, 1300);
        this.getServer().getPluginManager().registerEvents(new joinEvent(), this);
        this.getCommand("AsAntiVpn").setTabCompleter(new TabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public AsAntiVpn() {
        instance = this;
    }

    public static AsAntiVpn getInstance() {
        return instance;
    }
}
