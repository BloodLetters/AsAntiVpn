package me.bl.Event;

import me.bl.Service.IpApi;
import me.bl.Utils.Warna;
import me.bl.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.IOException;

public class NewJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerLoginEvent e) throws IOException {

        // check user country && broadcast
        if (main.getInstance().getConfig().getBoolean("On-Join.Show-Player-Country")) {
            main.getInstance().getServer().broadcastMessage(Warna.color(main.getInstance().getConfig().getString("Message.Broadcast-country").replace("%player%", e.getPlayer().getName().replace("%country%", IpApi.getCountry(e.getAddress().getHostAddress())))));
        }
    }
}
