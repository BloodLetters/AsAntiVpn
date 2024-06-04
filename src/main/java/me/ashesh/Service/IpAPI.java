package me.ashesh.Service;
import me.ashesh.utils.Request;
import org.json.JSONObject;

public class IpAPI {

    public static int req(String ip) {
        int rescode = 0;

        try {
            String req = Request.http("http://ip-api.com/json/" + ip + "?fields=status,message,country,countryCode,region,regionName,city,mobile,proxy,query");
            if (!req.equals("Error")) {
                JSONObject obj = new JSONObject(req);
                if (obj.get("status") == "fail") {
                    rescode = 2;
                } else {
                    if (obj.get("proxy") == "true") {
                        rescode = 1;
                    }
                }
            } else {
                rescode = 3;
            }
        } catch(Exception e) {
            rescode = 3;
        }

        return rescode;
    }
}
