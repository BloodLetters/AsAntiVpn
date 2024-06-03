package me.ashesh.Event;

import me.ashesh.AsAntiVpn;
import me.ashesh.Command.TabCompleter;
import me.ashesh.Manager.Algorithm;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.util.Objects;

public class joinEvent implements Listener {
    public AsAntiVpn ins = AsAntiVpn.getInstance();

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        String addr = e.getAddress().getHostAddress();
        try {
            if(Algorithm.request(Objects.requireNonNull(ins.getConfig().getString("Algorithm")), addr)) {
                Bukkit.getLogger().info("[AsAntiVpn] " + e.getName() + " Join with VPN");
                e.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, ins.getConfig().getString("Kick-Messager"));
            } else {
                Bukkit.getLogger().info("[AsAntiVpn] " + e.getName() + " Join without VPN");
            }
        } catch (Exception ex) {
            Bukkit.getLogger().severe(ex.getMessage());
        }
    }
}
