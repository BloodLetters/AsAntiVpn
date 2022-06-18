package me.bl.Utils;

import me.bl.main;

import java.io.IOException;
import java.util.List;

public class Blacklist {

    public static void write(String ip) throws IOException {

        if (!main.getInstance().getConfig().getBoolean("Hide-message.Added-to-blacklist")) {
            main.getInstance().getLogger().info(">> Adding " + ip + " to Blacklist!");
        }

        List<String> ips = main.getInstance().getCustomConfig().getStringList("Blacklist");
        ips.add(ip);
        main.getInstance().getCustomConfig().set("Blacklist", ips);
        main.getInstance().getCustomConfig().save(main.getInstance().getCustomConfigFile());
    }

    public static void remove(String ip) throws IOException {

        List<String> ips = main.getInstance().getCustomConfig().getStringList("Blacklist");
        ips.remove(ip);
        main.getInstance().getCustomConfig().set("Blacklist", ips);
        main.getInstance().getCustomConfig().save(main.getInstance().getCustomConfigFile());

    }
}
