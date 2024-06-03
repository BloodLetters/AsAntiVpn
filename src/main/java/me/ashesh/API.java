package me.ashesh;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class API {
    public static void debugConsole(String e) {
        if (AsAntiVpn.getInstance().getConfig().getBoolean("debug")) {
            Bukkit.getLogger().info("[MMOCoreEXP-Debug] " + e);
        }
    }

    public static String chatColorized(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
