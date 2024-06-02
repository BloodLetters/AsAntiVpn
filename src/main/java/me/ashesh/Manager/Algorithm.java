package me.ashesh.Manager;

import me.ashesh.Service.GetIpIntel;
import me.ashesh.Service.IpAPI;
import org.bukkit.Bukkit;

public class Algorithm {

    public static int getIpintel = 0;
    public static int IPAPI = 0;

    public static boolean Algorithm(String type, String ip) {
        boolean res = false;

        switch (type) {
            case "FREE":
                if (getIpintel <= 12) {
                    int r = GetIpIntel.req(ip);
                    res = r == 0;
                }  else if(IPAPI <= 30) {
                    int r = IpAPI.req(ip);
                    res = r == 0;
                } else {
                    Bukkit.getLogger().severe("[AsAntiVpn] Cant detect cause Free Service exceeds");
                }

            case "KEY":
                System.out.println("Dawg");
        }

        return res;
    }
}
