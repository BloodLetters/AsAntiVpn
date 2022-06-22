package me.bl.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.bl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpHunter {

    public static boolean isCanUse = true;

    public static boolean check(String ip) throws IOException {
        URL url = new URL("https://www.iphunter.info:8082/v1/ip/" + ip);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestProperty("X-Key", main.getInstance().getConfig().getString("Key.IpHunter"));

        BufferedReader readerv1 = new BufferedReader(new InputStreamReader(url.openStream()));

        Gson gsonv1 = new Gson();
        JsonObject jsonObject = gsonv1.fromJson(readerv1, JsonObject.class);

        // Status
        JsonElement status = jsonObject.get("status");
        String result = status.getAsString();

        if (result.equalsIgnoreCase("success")) {

            JsonElement elementData = jsonObject.get("data");
            JsonObject test = elementData.getAsJsonObject();

            JsonElement proxy = test.get("block");
            int isVPN = proxy.getAsInt();

            return isVPN == 1;

        } else {

            isCanUse = false;
            return ProxyCheck.Use(ip);

        }
    }
}
