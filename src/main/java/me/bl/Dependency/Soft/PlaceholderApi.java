package me.bl.Dependency.Soft;

import me.bl.Event.NewPrejoin;
import me.bl.Service.IpApi;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.IOException;

public class PlaceholderApi extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "Aril4511";
    }

    @Override
    public String getIdentifier() {
        return "AsAntiVpn";
    }

    @Override
    public String getVersion() {
        return "${project.version}";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("country")) {
            try {
                return IpApi.getCountry(NewPrejoin.ip);
            } catch (IOException e) {
                return "Error with Api";
            }
        }

        if(params.equalsIgnoreCase("ip")) {
            return NewPrejoin.ip;
        }

        if(params.equalsIgnoreCase("player")) {
            return NewPrejoin.name;
        }

        if(params.equalsIgnoreCase("protocolversion")) {
            if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
                return String.valueOf(NewPrejoin.protocolVersion);
            } else {
                return null;
            }
        }

        if(params.equalsIgnoreCase("version")) {
            if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
                return ViaVersion.check(NewPrejoin.protocolVersion);
            } else {
                return null;
            }
        }

        return null;
    }

}
