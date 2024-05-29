package me.ashesh.API;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ProxyCheck {

    int ProxyCheck(String ip, String key) {
        int rescode = 0;

        try {
            URL url = new URL("https://proxycheck.io/v2/" + ip + "?key=" + key + "&risk=1&vpn=1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            Gson gs = new Gson();
            JsonObject json = gs.fromJson(reader, JsonObject.class);

            if(json.get("status").equals("ok")) {
                JsonElement ipObj = json.get(ip);
                JsonObject obj = ipObj.getAsJsonObject();
                if (obj.get("proxy").getAsString().equalsIgnoreCase("yes")) {
                    rescode = 1;
                } else {
                    rescode = 0;
                }
            } else {
                rescode = 2;
            }

        } catch (Exception ex) {
            rescode = 2;
        }

        return rescode;
    }
}
