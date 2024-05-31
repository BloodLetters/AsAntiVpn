package me.ashesh;

import me.ashesh.Service.IpAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class Selection implements Listener {
    public static int IPAPI = 0;

    @EventHandler
    public static void onPreJoin(AsyncPlayerPreLoginEvent e) {
    }

    public static boolean method(String type, String ip) {

        switch (type) {
            case "FREE":
                int IpAPI = me.ashesh.Service.IpAPI.req(ip);
        }
    }
}
