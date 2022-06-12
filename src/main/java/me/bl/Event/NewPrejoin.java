package me.bl.Event;

import com.viaversion.viaversion.api.Via;
import me.bl.Core.Algorithm;
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

import java.io.IOException;
import java.util.Objects;

public class NewPrejoin implements Listener {

    public static String ip;
    public static String name;

    public static int protocolVersion;
    private static boolean ready = true;
    public static int MJPS = main.getInstance().getConfig().getInt("Kick-Algorithm.Slow-Join-Tick");

    public static int totalBlocked = 0;
    public static int totalJoin = 0;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPreJoin(AsyncPlayerPreLoginEvent e) throws IOException {
        ip = e.getAddress().getHostAddress();
        name = e.getName();

        // get protocol version
        protocolVersion = Via.getAPI().getPlayerVersion(e.getUniqueId());

        // slow join
        if (ready) {
            ready = false;
            Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> {
                ready = true;
            }, MJPS);


            // check whitelist player
            if (main.getInstance().getConfig().getStringList("Whitelist-Player").contains(e.getName())) {
                String conf = Objects.requireNonNull(main.getInstance().getConfig().getString(("Message.Whitelist-player-Message")));
                String repl = conf.replace("%player%", e.getName());
                main.getInstance().getLogger().info(repl);

                totalJoin++;
                // check whitelist ip
            } else if (main.getInstance().getConfig().getStringList("Whitelist-IP").contains(ip)) {
                String conf = Objects.requireNonNull(main.getInstance().getConfig().getString(("Message.Whitelist-ip-Message")));
                String repl = conf.replace("%player%", e.getName());
                main.getInstance().getLogger().info(repl);

                totalJoin++;
                // checking blacklist player
            } else if (main.getInstance().getCustomConfig().getStringList("Blacklist").contains(ip)) {

                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Blacklist-Message"))));

            } else {

                // check key token
                if (main.getInstance().getConfig().getString("Key.ProxyCheck").isEmpty()
                        || main.getInstance().getConfig().getString("Key.VpnApi").isEmpty()
                        || main.getInstance().getConfig().getString("Key.GetIPIntel").isEmpty()
                        || main.getInstance().getConfig().getString("Key.IpHub").isEmpty()) {

                    main.getInstance().getLogger().severe("Setup all your key in config. to use this plugin");

                } else {

                    if (Algorithm.algorithm(ip)) {

                        // VPN user!
                        main.getInstance().getServer().getLogger().info("[AsAntiVpn] >> Player " + e.getName() + " With ip " + ip + " using VPN");
                        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Kick-Message").replace("%player%", e.getName()))));

                        // add to vpn user counter
                        totalBlocked++;

                        // save user ip to Blacklist config
                        Blacklist.write(ip);

                        // Discord webhook
                        if (main.getInstance().getConfig().getBoolean("Discord.Enable")) {
                            if (main.getInstance().getConfig().getString("Discord.Link").isEmpty()) {
                                main.getInstance().getLogger().info("Webhook Link not found!");
                            } else {
                                WebhookHandler.Webhook(main.getInstance().getConfig().getString("Discord.Link"), e.getName());
                            }
                        }

                    } else {
                        // Non VPN user
                        main.getInstance().getServer().getLogger().info("[AsAntiVpn] >> Player " + e.getName() + " With ip " + ip + " Not using any VPN");

                        // add to counter
                        totalJoin++;

                    }
                }
            }
        } else {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Warna.color("&7[&eAntiVpn&7] &cPlease wait 1 second. before reconnect"));
        }
    }
}