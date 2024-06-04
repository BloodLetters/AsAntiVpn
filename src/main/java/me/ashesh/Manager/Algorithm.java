package me.ashesh.Manager;

import me.ashesh.AsAntiVpn;
import me.ashesh.Service.GetIpIntel;
import me.ashesh.Service.IpAPI;
import me.ashesh.Service.ProxyCheck;
import org.bukkit.Bukkit;

import java.util.Objects;

public class Algorithm {

    public static AsAntiVpn ins = AsAntiVpn.getInstance();
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
                } else if (IPAPI <= 30) {
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
                // Top-Down method
                if (Objects.equals(ins.getConfig().getString("Algorithm.method"), "method")) {

                    // ProxyCheck
                    int r = ProxyCheck.req(ip, ins.getConfig().getString("Key.ProxyCheck"));
                    if (r == 1) {
                        res = true;
                    }

                    if (r == 2) {
                        throw new Exception("ProxyCheck Services error fail to request data");
                    }

                    if (r == 3) {
                        throw new Exception("ProxyCheck Services error");
                    }

                    // VPnAPI
                    // TODO: ADD Services
                }

                // Continue
        }
        return res;
    }
}
