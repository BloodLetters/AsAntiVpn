package me.bl.Utils;

import me.bl.main;

public class Logger {
    public static void console(String message, boolean isColor) {
        if (isColor) {
            main.getInstance().getServer().getLogger().info(Warna.color(message));
        } else {
            main.getInstance().getServer().getLogger().info(message);
        }
    }
}
