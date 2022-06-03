package me.bl.Event;

import me.bl.Service.ProxyCheck;
import me.bl.Utils.Blacklist;
import me.bl.Utils.WebhookHandler;
import me.bl.main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.io.IOException;
import java.util.Objects;

public class PreJoin implements Listener {

    public static String ip;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) throws IOException {
        ip = e.getAddress().getHostAddress();

        // checking whitelist player
        if (main.getInstance().getConfig().getStringList("Whitelist-IP").contains(ip)) {
            main.getInstance().getLogger().info(">> " + e.getName() + " with ip " + ip + " whitelist in config!");

        } else {

            // chacking blacklist player
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