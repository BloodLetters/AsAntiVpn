package me.bl.Event;

import me.bl.Service.IpApi;
import me.bl.Service.ProxyCheck;
import me.bl.Utils.Blacklist;
import me.bl.Utils.Warna;
import me.bl.Utils.WebhookHandler;
import me.bl.main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.io.IOException;
import java.util.Objects;

public class PreJoin implements Listener {

    public static String ip;
    public static String country;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) throws IOException {
        ip = e.getAddress().getHostAddress();
        country = Objects.requireNonNull(IpApi.getCountry(e.getAddress().getHostAddress())).toLowerCase();

        // country checker
        if (main.getInstance().getConfig().getStringList("Blacklist-Country.Country-List").contains(country.toLowerCase())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Warna.color(main.getInstance().getConfig().getString("Message.Country-Blacklist")));

        // check user country
        } else if (main.getInstance().getConfig().getBoolean("Country.Enable")) {

            // broadcast message
            main.getInstance().getServer().broadcastMessage(Warna.color(main.getInstance().getConfig().getString("Country.Broadcast").replace("%player%", e.getName()).replace("%country%", country)));


        }else {

            // checking whitelist player name
            if (main.getInstance().getConfig().getStringList("Whitelist-Player").contains(e.getName())) {

                main.getInstance().getLogger().info(Warna.color(main.getInstance().getConfig().getString("Whitelist-player-Message").replace("%player%", e.getName())));

                // checking whitelist player ip
            } else if (main.getInstance().getConfig().getStringList("Whitelist-IP").contains(ip)) {

                main.getInstance().getLogger().info(Warna.color(main.getInstance().getConfig().getString("Whitelist-player-Message").replace("%player-ip%", e.getAddress().getHostAddress())));

            } else {

                // checking blacklist player
                if (main.getInstance().getCustomConfig().getStringList("Blacklist").contains(ip)) {

                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Blacklist-Message"))));

                } else {

                    // checking the vpn player
                    if (!Objects.requireNonNull(main.getInstance().getConfig().getString("Api-Key")).isEmpty()) {

                        if (ProxyCheck.Use(ip)) {

                            // VPN user!
                            main.getInstance().getServer().getLogger().info("[AsAntiVpn] >> Player " + e.getName() + " With ip " + ip + " using VPN");
                            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Kick-Message"))));

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

                        }
                    } else {

                        // if api-key is none
                        main.getInstance().getServer().getLogger().severe("[AsAntiVpn] >> Your token is none. setup it first to use AsAntiVpn");
                    }
                }
            }
        }
    }
}