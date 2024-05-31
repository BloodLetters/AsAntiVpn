package me.ashesh.Service;

import me.ashesh.utils.Request;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ProxyCheck {

    int ProxyCheck(String ip, String key) {
        int rescode = 0;
        JSONObject obj = new JSONObject(Request.http(ip));
        return 0;
    }
}
