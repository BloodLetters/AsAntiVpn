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

public class GetIpIntel {

    public static boolean check(String ip) throws IOException {
        URL urlv1 = new URL("https://check.getipintel.net/check.php?ip=" + ip + "&contact=" + main.getInstance().getConfig().getString("key.GetIPIntel") + "&format=json&flags=m");
        BufferedReader readerv1 = new BufferedReader(new InputStreamReader(urlv1.openStream()));

        // http code
        HttpURLConnection http = (HttpURLConnection) urlv1.openConnection();
        int statusCode = http.getResponseCode();

        if (statusCode == 429) {

            // reader
            Gson gsonv1 = new Gson();
            JsonObject jsonObjectv1 = gsonv1.fromJson(readerv1, JsonObject.class);

            JsonElement security = jsonObjectv1.get("status");
            String raw = security.getAsString().toLowerCase();

            if (raw.equalsIgnoreCase("success")) {

                JsonElement res = jsonObjectv1.get("result");
                int isProxy = res.getAsInt();
                return isProxy == 1;
            } else {

                // get error message
                JsonElement res = jsonObjectv1.get("message");
                String errmsg = res.getAsString();
                main.getInstance().getLogger().severe(errmsg);
                return false;

            }


        } else {

            // error key has limited
            main.getInstance().getLogger().severe(main.getInstance().getConfig().getString("Api-usgae-Limit"));
            return false;
        }
    }
}
