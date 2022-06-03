package me.bl;

import me.bl.Event.Command;
import me.bl.Event.PreJoin;
import me.bl.Utils.VersionChecker;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class main extends JavaPlugin implements Listener {

    private static main instance;

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Register All Event");
        getServer().getPluginManager().registerEvents(new PreJoin(), this);
        getCommand("AsAntiVpn").setExecutor(new Command());

        getLogger().info("Saving default config");
        saveDefaultConfig();

        // Check Blacklist config
        if (main.getInstance().getConfig().getBoolean("Blacklist.Enable")) {
            getLogger().info("Creating Blacklist.yml");
            createCustomConfig();
        }

        // register bStats
        getLogger().info("Registering bStats!");
        int pluginId = 13378;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("players", () -> "OnlinePlayers"));

        // checking version
        getLogger().info("Checking latest version...");
        try {

            if (getDescription().getVersion().equalsIgnoreCase(VersionChecker.check())) {
                getLogger().info("You running AsAntiVpn in latest version!");
            } else {
                getLogger().info("Pls update your AsAntiVpn. You running " + getDescription().getVersion() + " Latest " + VersionChecker.check());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public File getCustomConfigFile() {
        return this.customConfigFile;
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "blacklist.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("blacklist.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.println("AWDIJIAWJD");
    }

    public main() {
        instance = this;
    }

    public static main getInstance() {
        return instance;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
    }
}
