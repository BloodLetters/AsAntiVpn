package me.ashesh.Service;
import me.ashesh.utils.Request;
import org.json.JSONObject;

import java.util.Objects;

public class IpAPI {

    public static int req(String ip) {
        int rescode = 0;

        try {
            String req = Request.http(ip);
            if (!Objects.equals(req, "Error")) {
                JSONObject obj = new JSONObject(req);
                if (obj.get("status") == "fail") {
                    rescode = 2;
                } else {
                    if (obj.get("proxy") == "true") {
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