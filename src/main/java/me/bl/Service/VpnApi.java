package me.bl.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.bl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class VpnApi {


    public static boolean check(String ip) throws IOException {
        URL urlv1 = new URL("https://vpnapi.io/api/" + ip + "?key=" + main.getInstance().getConfig().getString("Key.VpnApi"));
        BufferedReader readerv1 = new BufferedReader(new InputStreamReader(urlv1.openStream()));

        Gson gsonv1 = new Gson();
        JsonObject jsonObjectv1 = gsonv1.fromJson(readerv1, JsonObject.class);

        JsonElement security = jsonObjectv1.get("security");
        JsonObject raw = security.getAsJsonObject();

        JsonElement vpn = raw.get("vpn");
        JsonElement proxy = raw.get("proxy");

        boolean isVPN = vpn.getAsBoolean();
        boolean isProxy = proxy.getAsBoolean();

        if (isVPN) {
            return true;
        } else if (isProxy) {
            return true;
        } else {
            return ProxyCheck.Use(ip);
        }
    }
}
