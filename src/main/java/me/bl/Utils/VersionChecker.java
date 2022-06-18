package me.bl.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {

    public static String check() throws IOException {
        URL urlv1 = new URL("https://api.spiget.org/v2/resources/97701/versions/latest");
        BufferedReader readerv1 = new BufferedReader(new InputStreamReader(urlv1.openStream()));

        // parse
        Gson gsonv1 = new Gson();
        JsonObject jsonObjectv1 = gsonv1.fromJson(readerv1, JsonObject.class);

        // version
        JsonElement status = jsonObjectv1.get("name");
        return status.getAsString();
    }
}
