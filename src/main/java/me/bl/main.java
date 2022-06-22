package me.bl;

import me.bl.Core.ConsoleFilter;
import me.bl.Event.Command;
import me.bl.Event.NewJoin;
import me.bl.Event.NewPrejoin;
import me.bl.Event.TabComplete;
import me.bl.Utils.SaveData;
import me.bl.Utils.VersionChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
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
        registerLoggerFilters(new ConsoleFilter());

        getCommand("AsAntiVpn").setExecutor(new Command());
        getCommand("AsAntiVpn").setTabCompleter(new TabComplete());


        getLogger().info("Saving default config");
        saveDefaultConfig();

        // Check Blacklist config
        getLogger().info("Creating Blacklist.yml");
        createCustomConfig();

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

        // checking dependency
        getLogger().info("Checking All dependency");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
           getLogger().info("Softdepency PlaceholderAPI detected!");
        } else {
            getLogger().info("Softdepency PlaceholderAPI not detected!");
        }

        if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
            getLogger().info("Softdepency ViaVersion detected!");
        } else {
            getLogger().info("Softdepency ViaVersion not detected!");
        }

        getServer().getPluginManager().registerEvents(new NewPrejoin(), this);
        getServer().getPluginManager().registerEvents(new NewJoin(), this);

        Bukkit.getScheduler().runTaskLater(this, () -> {
            try {
                getLogger().info("Starting saving Blacklist.yml");
                SaveData.saveNonVpn();
                SaveData.saveVPN();
                NewPrejoin.totalBlocked = 0;
                NewPrejoin.totalJoin = 0;
            } catch (IOException e) {
                getLogger().severe("Error while AutoSave Blacklist.yml, Message: " + e.getMessage());
            }
        }, (20L * 60) * 5);

    }

    @Override
    public void onDisable() {

        getLogger().info("Saving Data");
        try {
            SaveData.saveVPN();
            SaveData.saveNonVpn();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void registerLoggerFilters(Filter... filters) {
        org.apache.logging.log4j.Logger rootLogger = LogManager.getRootLogger();
        if (!(rootLogger instanceof Logger)) {
            return;
        }

        Logger logger = (Logger) rootLogger;
        for (Filter filter : filters) {
            logger.addFilter(filter);
        }
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
