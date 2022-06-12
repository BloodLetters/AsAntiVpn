package me.bl.Core;

import me.bl.Service.GetIpIntel;
import me.bl.Service.IpHub;
import me.bl.Service.ProxyCheck;
import me.bl.Service.VpnApi;
import me.bl.main;
import org.bukkit.Bukkit;

import java.io.IOException;

public class Algorithm {

    private static boolean ready = true;
    public static int counter = 1;
    public static int MJPS = 8;

    public static boolean algorithm(String ip) throws IOException {

        // Counter 1
        if (counter == 1) {

            // increment
            counter++;

            if (main.getInstance().getConfig().getBoolean("Kick-Algorithm.Show-API-name-onjoin")) {
                main.getInstance().getLogger().info(ip + " Detect using ProxyCheck");
            }

            return ProxyCheck.Use(ip);

            // Counter 2
        } else if (counter == 2) {

            // increment
            counter++;

            if (main.getInstance().getConfig().getBoolean("Kick-Algorithm.Show-API-name-onjoin")) {
                main.getInstance().getLogger().info(ip + " Detect using VpnApi");
            }

            return VpnApi.check(ip);

            // Counter 3
        } else if (counter == 3) {

            // increment
            counter++;

            if (main.getInstance().getConfig().getBoolean("Kick-Algorithm.Show-API-name-onjoin")) {
                main.getInstance().getLogger().info(ip + " Detect using IpHub");
            }

            return IpHub.check(ip);

            // counter 4
        } else if (counter == 4) {
            if (ready) {
                ready = false;
                Bukkit.getScheduler().runTaskLater(main.getInstance(), () -> {
                    ready = true;
                }, (20L * MJPS));

                // increment
                counter = 1;

                if (main.getInstance().getConfig().getBoolean("Kick-Algorithm.Show-API-name-onjoin")) {
                    main.getInstance().getLogger().info(ip + " Detect using GetIpIntel");
                }

                return GetIpIntel.check(ip);

            } else {
                counter = 1;
            }
        }

        return false;
    }
}