package me.bl.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.bl.Utils.Warna;
import me.bl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpApi {

    public static String getCountry(String ip) throws IOException {
        URL url = new URL("http://ip-api.com/json/" + ip);
        BufferedReader readerv1 = new BufferedReader(new InputStreamReader(url.openStream()));

        Gson gsonv1 = new Gson();
        JsonObject jsonObjectv1 = gsonv1.fromJson(readerv1, JsonObject.class);

        // status code
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        int statusCode = http.getResponseCode();

        // api error
        if (statusCode == 429) {

            main.getInstance().getLogger().info(Warna.color(main.getInstance().getConfig().getString("Api-Limit").replace("%player-ip%", ip)));

        } else {

            // location
            JsonElement status = jsonObjectv1.get("status");
            String result = status.getAsString();

            if (result.equalsIgnoreCase("success")) {

                JsonElement raw = jsonObjectv1.get("country");
                return raw.getAsString();

            // api limite
            } else {

                main.getInstance().getLogger().info(Warna.color(main.getInstance().getConfig().getString("Api-Limit").replace("%player-ip%", ip)));
            }
        }
        return null;
    }
}
