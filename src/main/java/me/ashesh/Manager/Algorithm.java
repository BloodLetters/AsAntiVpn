package me.ashesh.Manager;

import me.ashesh.Service.GetIpIntel;
import me.ashesh.Service.IpAPI;
import org.bukkit.Bukkit;

public class Algorithm {

    public static int getIpintel = 0;
    public static int IPAPI = 0;

    public static boolean request(String type, String ip) throws Exception {
        boolean res = false;

        switch (type) {
            case "FREE":
                if (getIpintel <= 12) {
                    int r = GetIpIntel.req(ip);
                    if (r == 1) {
                        res = true;
                    }

                    if (r == 2) {
                        throw new Exception("GetIpIntel Services error fail to request data");
                    }

                    if (r == 3) {
                        throw new Exception("GetIpIntel Services error");
                    }
                }  else if(IPAPI <= 30) {
                    int r = IpAPI.req(ip);
                    if (r == 1) {
                        res = true;
                    }

                    if (r == 2) {
                        throw new Exception("Ip-API Services error fail to request data");
                    }

                    if (r == 3) {
                        throw new Exception("IP-API Services error");
                    }
                } else {
                    Bukkit.getLogger().severe("[AsAntiVpn] Cant detect cause Free Service exceeds limit");
                }

            case "KEY":
                System.out.println("Dawg");
        }

        return res;
    }
}
