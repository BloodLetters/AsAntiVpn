package me.bl.Utils;

import me.bl.Event.NewPrejoin;
import me.bl.main;

import java.io.IOException;

public class SaveData {

    public static void saveVPN() throws IOException {

        int ips = main.getInstance().getCustomConfig().getInt("Block-Amount.Vpn");
        main.getInstance().getCustomConfig().set("Block-Amount.Vpn", ips + NewPrejoin.totalBlocked);
        main.getInstance().getCustomConfig().save(main.getInstance().getCustomConfigFile());

    }

    public static void saveNonVpn() throws IOException {

        int ips = main.getInstance().getCustomConfig().getInt("Block-Amount.Normal");
        main.getInstance().getCustomConfig().set("Block-Amount.Normal", ips + NewPrejoin.totalJoin);
        main.getInstance().getCustomConfig().save(main.getInstance().getCustomConfigFile());

    }

    public static int loadVPN() {
        return main.getInstance().getCustomConfig().getInt("Block-Amount.Vpn");
    }

    public static int loadNonVpn() {
        return main.getInstance().getCustomConfig().getInt("Block-Amount.Normal");
    }

    public static main instance() {
        return main.getInstance();
    }
}