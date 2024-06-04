package me.ashesh.Service;

import me.ashesh.AsAntiVpn;
import me.ashesh.utils.Request;
import org.json.JSONObject;

public class GetIpIntel {

    public static AsAntiVpn ins = AsAntiVpn.getInstance();

    public static int req(String ip) {
        int rescode = 0;

        try {
            String req = Request.http("https://check.getipintel.net/check.php?ip=" + ip + "&contact=" + ins.getConfig().getString("key.GetIpintel") + "&format=json&flags=m");
            if (!req.equals("Error")) {
                JSONObject obj = new JSONObject(req);
                if (obj.get("status") == "error") {
                    rescode = 2;
                } else {
                    if (obj.get("result") == "1") {
                        rescode = 1;
                    }
                }
            } else {
                rescode = 3;
            }
        } catch (Exception e) {
            rescode = 3;
        }

        return rescode;
    }

}
