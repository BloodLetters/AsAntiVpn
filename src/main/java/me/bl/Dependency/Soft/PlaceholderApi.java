package me.bl.Dependency.Soft;

import me.bl.Event.OldPreJoin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

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
        return "2.1.5";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("country")) {
            return OldPreJoin.country;
        }

        if(params.equalsIgnoreCase("ip")) {
            return OldPreJoin.ip;
        }

        if(params.equalsIgnoreCase("player")) {
            return OldPreJoin.name;
        }

        if(params.equalsIgnoreCase("protocolversion")) {
            if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
                return String.valueOf(OldPreJoin.protocolVersion);
            } else {
                return null;
            }
        }

        if(params.equalsIgnoreCase("version")) {
            if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
                return ViaVersion.check(OldPreJoin.protocolVersion);
            } else {
                return null;
            }
        }

        return null;
    }

}
