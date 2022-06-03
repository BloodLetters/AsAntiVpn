package me.bl.Utils;

import org.bukkit.ChatColor;

public class Warna {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
