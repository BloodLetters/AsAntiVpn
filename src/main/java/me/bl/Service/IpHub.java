package me.bl.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.bl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpHub {

    public static boolean isCanUse = false;

    public static boolean check(String ip) throws IOException {
        URL url = new URL("http://v2.api.iphub.info/ip/" + ip);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestProperty("X-Key", main.getInstance().getConfig().getString("Key.IpHub"));

        if (request.getResponseCode() == 200) {

            BufferedReader readerv1 = new BufferedReader(new InputStreamReader((InputStream) request.getContent()));

            Gson gsonv1 = new Gson();
            JsonObject jsonObjectv1 = gsonv1.fromJson(readerv1, JsonObject.class);

            JsonElement security = jsonObjectv1.get("block");
            int raw = security.getAsInt();

            // non vpn
            if (raw == 0) {
                isCanUse = true;
                return false;

                // vpn
            } else if (raw == 1) {
                isCanUse = true;
                return true;

                // residential ip
            } else if (raw == 2) {
                isCanUse = true;
                return false;
            }

        } else {

            isCanUse = false;
            return ProxyCheck.Use(ip);

        }

        return false;
    }
}