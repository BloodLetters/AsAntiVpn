package me.bl.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.bl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GetIpIntel {

    public static boolean isCanUse = true;
    public static String ErrMsg;

    public static boolean check(String ip) throws IOException {

        URL urlv1 = new URL("https://check.getipintel.net/check.php?ip=" + ip + "&contact=" + main.getInstance().getConfig().getString("Key.GetIPIntel") + "&format=json&flags=m");
        BufferedReader readerv1 = new BufferedReader(new InputStreamReader(urlv1.openStream()));

        // reader
        Gson gsonv1 = new Gson();
        JsonObject jsonObjectv1 = gsonv1.fromJson(readerv1, JsonObject.class);

        JsonElement security = jsonObjectv1.get("status");
        String raw = security.getAsString();

        if (raw.equalsIgnoreCase("success")) {

            JsonElement res = jsonObjectv1.get("result");
            int isProxy = res.getAsInt();

            isCanUse = true;
            return isProxy == 1;

        } else {
            isCanUse = false;

            JsonElement res = jsonObjectv1.get("message");
            ErrMsg = res.getAsString();

            return ProxyCheck.Use(ip);
        }
    }
}
