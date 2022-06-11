package me.bl.Event;

import com.viaversion.viaversion.api.Via;
import me.bl.Service.IpApi;
import me.bl.Service.ProxyCheck;
import me.bl.Utils.Blacklist;
import me.bl.Utils.Warna;
import me.bl.Utils.WebhookHandler;
import me.bl.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.Objects;

public class NewPrejoin implements Listener {

    public static String ip;
    public static String country;
    public static String name;

    public static int protocolVersion;
    private static boolean ready = true;
    public static int MJPS = main.getInstance().getConfig().getInt("Kick-Algorithm.Slow-Join-Tick");
    public static String kick_message;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(AsyncPlayerPreLoginEvent e) throws IOException {
        ip = e.getAddress().getHostAddress();
        country = Objects.requireNonNull(IpApi.getCountry(e.getAddress().getHostAddress())).toLowerCase();
        name = e.getName();

        // get protocol version
        protocolVersion = Via.getAPI().getPlayerVersion(e.getUniqueId());

        // slow join
        if (ready) {
            ready = false;
            Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> {
                ready = true;
            }, MJPS);



        } else {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "&7[&eAntiVpn&7] &cPlease wait 1 second. before reconnect");
        }
    }
}