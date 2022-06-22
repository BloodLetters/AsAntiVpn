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
import java.util.Objects;

public class IpApi {

    public static String getCountry(String ip) throws IOException {
        URL url = new URL("http://ip-api.com/json/" + ip);

        // status code
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        int statusCode = http.getResponseCode();

        // api error
        if (statusCode == 429) {
            String conf = Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Api-Limit"));
            String repl = conf.replace("%player-ip%", ip);
            main.getInstance().getLogger().info(Warna.color(repl));

            return "Null";

        } else {

            if (200 <= statusCode && statusCode <= 299) {

                // reader
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                Gson gsonv1 = new Gson();
                JsonObject jsonObject = gsonv1.fromJson(reader, JsonObject.class);

                // location
                JsonElement status = jsonObject.get("status");
                String result = status.getAsString();

                if (result.equalsIgnoreCase("success")) {

                    JsonElement raw = jsonObject.get("country");
                    return raw.getAsString();


                } else {

                    String conf = Objects.requireNonNull(main.getInstance().getConfig().getString("Api-Limit"));
                    String repl = conf.replace("%player-ip%", ip);
                    main.getInstance().getLogger().info(Warna.color(repl));

                    return "Null";
                }

            } else {

                String conf = Objects.requireNonNull(main.getInstance().getConfig().getString("Message.Api-Limit"));
                String repl = conf.replace("%player-ip%", ip);
                main.getInstance().getLogger().info(Warna.color(repl));

                return "Null";

            }
        }
    }
}
