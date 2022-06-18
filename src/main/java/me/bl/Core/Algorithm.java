package me.bl.Core;

import me.bl.Service.GetIpIntel;
import me.bl.Service.IpHub;
import me.bl.Service.ProxyCheck;
import me.bl.Service.VpnApi;
import me.bl.Utils.Warna;
import me.bl.main;
import org.bukkit.Bukkit;

import java.io.IOException;

public class Algorithm {

    private static boolean ready = true;
    public static int counter = 1;
    public static int MJPS = 8;
    public static int getType = main.getInstance().getConfig().getInt("Kick-Algorithm.Use-Algorithm");

    public static boolean algorithm(String ip) throws IOException {


        if (getType == 1) {

            return TopDown(ip);
        } else if (getType == 2) {

            return CheckAll(ip);
        } else if (getType == 3) {

            return Check1by1(ip);
        } else if (getType == 4) {

            return ProxyCheck.Use(ip);
        } else {
            return TopDown(ip);
        }
    }

    public static boolean Check1by1(String ip) throws IOException {
        if (ProxyCheck.isCanUse) {
            return ProxyCheck.Use(ip);
        } else if (VpnApi.isCanuse) {
            return VpnApi.check(ip);
        } else if (IpHub.isCanUse) {
            return IpHub.check(ip);
        } else {
            main.getInstance().getLogger().severe(Warna.color(main.getInstance().getConfig().getString("Message.Api-usgae-Limit")));
            return false;
        }
    }

    public static boolean CheckAll(String ip) throws IOException {
        int d_c = 0;

        boolean a = ProxyCheck.Use(ip);
        boolean b = VpnApi.check(ip);
        boolean c = IpHub.check(ip);
        boolean d = GetIpIntel.check(ip);

        if (a) {
            d_c++;
        }

        if (b) {
            d_c++;
        }

        if (c) {
            d_c++;
        }

        if (d) {
            d_c++;
        }

        return d_c >= 2;
    }

    public static boolean TopDown(String ip) throws IOException {
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