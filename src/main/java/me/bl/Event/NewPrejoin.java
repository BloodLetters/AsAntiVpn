package me.bl.Event;

import com.viaversion.viaversion.api.Via;
import me.bl.Core.Algorithm;
import me.bl.Service.IpApi;
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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class NewPrejoin implements Listener {

    public static String ip;
    public static String country;
    public static String name;

    public static int protocolVersion;
    private static boolean ready = true;
    public static int MJPS = main.getInstance().getConfig().getInt("Kick-Algorithm.Slow-Join-Tick");

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPreJoin(AsyncPlayerPreLoginEvent e) throws IOException {
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

            // country
            if (main.getInstance().getConfig().getBoolean("Blacklist-Country.Enable")) {
                if (main.getInstance().getConfig().getStringList("Blacklist-Country.Country-List").contains(country.toLowerCase())) {
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Warna.color(main.getInstance().getConfig().getString("Message.Country-Blacklist")));
                }

                // check whitelist player
            } else if (main.getInstance().getConfig().getStringList("Whitelist-Player").contains(e.getName())) {
                String conf = Objects.requireNonNull(main.getInstance().getConfig().getString(("Message.Whitelist-player-Message")));
                String repl = conf.replace("%player%", e.getName());
                main.getInstance().getLogger().info(repl);

                // check whitelist ip
            } else if (main.getInstance().getConfig().getStringList("Whitelist-IP").contains(ip)) {
                String conf = Objects.requireNonNull(main.getInstance().getConfig().getString(("Message.Whitelist-ip-Message")));
                String repl = conf.replace("%player%", e.getName());
                main.getInstance().getLogger().info(repl);

            } else {

                // check key token
                if (main.getInstance().getConfig().getString("Key.ProxyCheck").isEmpty()
                        || main.getInstance().getConfig().getString("Key.VpnApi").isEmpty()
                        || main.getInstance().getConfig().getString("Key.GetIPIntel").isEmpty()) {

                    main.getInstance().getLogger().severe("Setup all your key in config. to use this plugin");

                } else {

                    // checking blacklist player
                    if (main.getInstance().getConfig().getBoolean("Blacklist.Enable")) {
                        if (main.getInstance().getCustomConfig().getStringList("Blacklist").contains(ip)) {
                            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Blacklist-Message"))));
                        }
                    } else {


                        // error di sini 1 dan 3
                        if (Algorithm.algorithm(ip)) {

                            // VPN user!
                            main.getInstance().getServer().getLogger().info("[AsAntiVpn] >> Player " + e.getName() + " With ip " + ip + " using VPN");
                            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Kick-Message").replace("%player%", e.getName()))));

                            // save user ip to Blacklist config
                            if (main.getInstance().getConfig().getBoolean("Blacklist.Enable")) {
                                Blacklist.write(ip);
                            }

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

                            // broadcast country
                            if (main.getInstance().getConfig().getBoolean("Country.Enable")) {
                                main.getInstance().getServer().broadcastMessage(Warna.color(main.getInstance().getConfig().getString("Country.Broadcast").replace("%player%", e.getName()).replace("%country%", country)));
                            }

                        }
                    }
                }
            }
        } else {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "&7[&eAntiVpn&7] &cPlease wait 1 second. before reconnect");
        }
    }
}