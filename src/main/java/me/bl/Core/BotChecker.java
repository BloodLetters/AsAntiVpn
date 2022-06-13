package me.bl.Core;

import me.bl.Service.GetIpIntel;
import me.bl.main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class BotChecker implements Listener {

    private static boolean bot_ready = true;
    public static boolean bot_isBot = false;
    private static int bot_CPS = 0;


    @EventHandler(priority = EventPriority.LOWEST)
    public static void checkBot(AsyncPlayerPreLoginEvent e) {

        if (bot_ready) {
            bot_ready = false;
            bot_isBot = false;
            Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> {
                bot_ready = true;
                bot_CPS = 0;
            }, 20L);

            Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> {
                bot_isBot = false;
            }, 20L * 10);

        } else {
            if (bot_CPS >= 3) {
                bot_isBot = true;
            } else {
                bot_CPS++;
            }
        }
    }
}
