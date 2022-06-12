package me.bl.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.bl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ProxyCheck {

    public static String ErrMsg;
    public static boolean isCanUse = true;

    public static boolean Use(String ip) throws IOException {
        URL urlv1 = new URL("https://proxycheck.io/v2/" + ip + "?key=" + main.getInstance().getConfig().getString("Key.ProxyCheck") + "&vpn=1");
        BufferedReader readerv1 = new BufferedReader(new InputStreamReader(urlv1.openStream()));

        Gson gsonv1 = new Gson();
        JsonObject jsonObjectv1 = gsonv1.fromJson(readerv1, JsonObject.class);

        // Status
        JsonElement status = jsonObjectv1.get("status");
        String result = status.getAsString();

        if(result.equalsIgnoreCase("ok")) {

            // Vpn
            JsonElement downloadsElementv1 = jsonObjectv1.get(ip);
            JsonObject test = downloadsElementv1.getAsJsonObject();

            JsonElement proxy = test.get("proxy");
            String proxyStatus = proxy.getAsString();

            return proxyStatus.equalsIgnoreCase("yes");
        } else if (result.equalsIgnoreCase("denied")) {

            // warning message
            JsonElement error = jsonObjectv1.get("message");
            String  message = error.getAsString();
            main.getInstance().getServer().getLogger().severe("[AsAntiVpn] >> Error With Api. Message: " + "'" + message + "'");
            ErrMsg = message;
            isCanUse = false;

            return false;
        } else if (result.equalsIgnoreCase("error")) {

            // error message
            JsonElement error = jsonObjectv1.get("message");
            String message = error.getAsString();
            main.getInstance().getServer().getLogger().severe("[AsAntiVpn] >> Error With Api. Message: " + "'" + message + "'");
            ErrMsg = message;
            isCanUse = false;

            return false;
        }

        return false;
    }
}
