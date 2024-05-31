package me.ashesh.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.http.HttpClient;

public class Request {
    public static String http(String ip) {
        try {
            URL url = new URL(ip);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            return reader.readLine();
        } catch (IOException ex) {
            return "error";
        }
    }
}
