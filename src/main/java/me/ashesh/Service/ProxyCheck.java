package me.ashesh.Service;

import me.ashesh.AsAntiVpn;
import me.ashesh.utils.Request;
import org.json.JSONObject;

import java.util.Objects;

public class ProxyCheck {

    public static AsAntiVpn ins = AsAntiVpn.getInstance();

    public static int req(String ip, String key) {
        int rescode = 0;

        try {
            String req = Request.http("https://proxycheck.io/v2/" + ip + "?key=" + ins.getConfig().getString("key.ProxyCheck") + "&risk=1&vpn=1");
            JSONObject obj = new JSONObject(req);
            if (!req.equals("Error")) {
                if (obj.get("status") == "fail") {
                    rescode = 2;
                } else {
                    JSONObject security = obj.getJSONObject(ip);
                    if (security.get("proxy") == "yes") {
                        rescode = 1;
                    }
                }
            } else {
                rescode = 3;
            }
        } catch(Exception e) {
            rescode = 3;
        }

        return rescode;
    }
}
