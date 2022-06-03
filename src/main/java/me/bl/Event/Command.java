package me.bl.Event;

import me.bl.Utils.Blacklist;
import me.bl.Utils.Warna;
import me.bl.main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.Objects;

public class Command implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("AsAntiVpn")) {
            if (args.length == 0) {

                // main command
                if (sender.hasPermission("asantivpn.help")) {
                    sender.sendMessage(Warna.color("&7-------------------------------"));
                    sender.sendMessage(Warna.color(""));
                    sender.sendMessage(Warna.color("  &7- &b1). &a/AsAntiVpn help"));
                    sender.sendMessage(Warna.color("  &7- &b2). &a/AsAntiVpn reload"));
                    sender.sendMessage(Warna.color("  &7- &b3). &a/AsAntiVpn add <ip>"));
                    sender.sendMessage(Warna.color("  &7- &b4). &a/AsAntiVpn remove <ip>"));
                    sender.sendMessage(Warna.color(""));
                    sender.sendMessage(Warna.color("&7-------------------------------"));
                } else {
                    sender.sendMessage(Warna.color(main.getInstance().getConfig().getString("Message.NoPermission")));
                }
            }

            // Reload command
            else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("asantivpn.reload")) {
                    try {
                        main.getInstance().getCustomConfig().load(main.getInstance().getCustomConfigFile());
                        main.getInstance().reloadConfig();
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] &aPlugin reloaded!"));
                    } catch (IOException | InvalidConfigurationException e) {
                        e.printStackTrace();
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] &4Error has occured. check in console!"));
                    }
                } else {
                    sender.sendMessage(Objects.requireNonNull(main.getInstance().getConfig().getString("Message.NoPermission")));
                }
            }

            // help command
            else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(Warna.color("&7-------------------------------"));
                sender.sendMessage(Warna.color(""));
                sender.sendMessage(Warna.color("  &7- &b1). &a/AsAntiVpn help"));
                sender.sendMessage(Warna.color("  &7- &b2). &a/AsAntiVpn reload"));
                sender.sendMessage(Warna.color("  &7- &b3). &a/AsAntiVpn add <ip>"));
                sender.sendMessage(Warna.color("  &7- &b4). &a/AsAntiVpn remove <ip>"));
                sender.sendMessage(Warna.color(""));
                sender.sendMessage(Warna.color("&7-------------------------------"));
            }

            // add ip to blacklist
            else if (args[0].equalsIgnoreCase("add")) {
                if (sender.hasPermission("asantivpn.add")) {
                    if (args.length == 1) {
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] &cIncorrect CMD!. example: &a/AsAntiVpn add 1.1.1.1"));
                    } else if (args.length == 2) {
                        try {
                            Blacklist.write(args[1]);
                            sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] &aSuccess to add &e" + args[1]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // remove ip from blacklist
            else if (args[0].equalsIgnoreCase("remove")) {
                if (sender.hasPermission("asantivpn.add")) {
                    if (args.length == 1) {
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] &cIncorrect CMD!. example: &a/AsAntiVpn remove 1.1.1.1"));
                    } else if (args.length == 2) {
                        try {
                            Blacklist.remove(args[1]);
                            sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] &aSuccess to remove &e" + args[1]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }
}
