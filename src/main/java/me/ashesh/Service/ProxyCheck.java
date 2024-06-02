package me.ashesh.Service;

import me.ashesh.utils.Request;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class ProxyCheck {

    public static int req(String ip, String key) {
        int rescode = 0;

        try {
            String req = Request.http(ip);
            JSONObject obj = new JSONObject(req);
            if (!req.equals("Error")) {
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
