package me.ashesh.Service;

import me.ashesh.AsAntiVpn;
import me.ashesh.utils.Request;
import org.json.JSONObject;

public class VpnAPI {

    public static AsAntiVpn ins = AsAntiVpn.getInstance();

    public static int req(String ip, String key) {
        int rescode = 0;

        try {
            String req = Request.http("https://vpnapi.io/api/" + ip + "?key=" + ins.getConfig().getString("key.VpnAPI"));
            JSONObject obj = new JSONObject(req);
            if (!req.equals("Error")) {
                if (obj.has("message")) {
                    rescode = 2;
                } else {
                    JSONObject security = obj.getJSONObject("security");
                    if (security.getBoolean("vpn") || security.getBoolean("proxy")) {
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
