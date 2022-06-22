package me.bl.Event;

import me.bl.Service.*;
import me.bl.Utils.Blacklist;
import me.bl.Utils.SaveData;
import me.bl.Utils.Warna;
import me.bl.main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

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
                    sender.sendMessage(Warna.color("  &7- &b5). &a/AsAntiVpn inspect <player>"));
                    sender.sendMessage(Warna.color("  &7- &b6). &a/AsAntiVpn check <ip>"));
                    sender.sendMessage(Warna.color("  &7- &b7). &a/AsAntiVpn info"));
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
                        SaveData.saveVPN();
                        SaveData.saveNonVpn();
                        NewPrejoin.totalBlocked = 0;
                        NewPrejoin.totalJoin = 0;
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
                sender.sendMessage(Warna.color("  &7- &b5). &a/AsAntiVpn inspect <player>"));
                sender.sendMessage(Warna.color("  &7- &b6). &a/AsAntiVpn check <ip>"));
                sender.sendMessage(Warna.color("  &7- &b7). &a/AsAntiVpn info"));
                sender.sendMessage(Warna.color(""));
                sender.sendMessage(Warna.color("&7-------------------------------"));
            }

            // add ip to blacklist
            else if (args[0].equalsIgnoreCase("add")) {
                if (sender.hasPermission("asantivpn.add")) {
                    if (args.length == 1) {
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] " + main.getInstance().getConfig().getString("Message.Blacklist.Ip-Add")));
                    } else if (args.length == 2) {
                        try {
                            Blacklist.write(args[1]);
                            sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] " + main.getInstance().getConfig().getString("Message.Blacklist.Ip-Add-Success").replace("%player-ip%", args[1])));
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
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] " + main.getInstance().getConfig().getString("Message.Blacklist.Ip-Remove")));
                    } else if (args.length == 2) {
                        try {
                            Blacklist.remove(args[1]);
                            sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] " + main.getInstance().getConfig().getString("Message.Blacklist.Ip-Remove-Success").replace("%player-ip%", args[1])));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // inspect command
            else if (args[0].equalsIgnoreCase("inspect")) {
                if (sender.hasPermission("asantivpn.inspect")) {
                    if (args.length == 1) {
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] " + main.getInstance().getConfig().getString("Message.Inspect")));
                    } else if (args.length == 2) {
                        Player t = Bukkit.getPlayer(args[1]);
                        if (t != null) {
                            try {
                                String ip = Objects.requireNonNull(t.getAddress()).getHostName();
                                String country = IpApi.getCountry(ip);

                                sender.sendMessage(Warna.color("&7&m--------------------"));
                                sender.sendMessage("");
                                sender.sendMessage(Warna.color(" &7- &eIP: &b" + ip));
                                sender.sendMessage(Warna.color(" &7- &eCountry: &b" + country));
                                sender.sendMessage(Warna.color(" &7- &eIsVpn: &b" + ProxyCheck.Use(ip)));
                                sender.sendMessage("");
                                sender.sendMessage(Warna.color("&7&m---------------------"));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            sender.sendMessage(Warna.color("&7[&c!&7] &cPlayer " + args[1] + " not found!"));
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("check")) {
                if (sender.hasPermission("asantivpn.check")) {
                    if (args.length == 1) {
                        sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] " + main.getInstance().getConfig().getString("Message.Check-ip")));
                    } else if (args.length == 2) {
                        sender.sendMessage(Warna.color("&7[DEBUG] Running. pls wait"));
                        try {

                            // api check
                            boolean a = ProxyCheck.Use(args[1]);
                            boolean b = VpnApi.check(args[1]);
                            boolean c = GetIpIntel.check(args[1]);
                            boolean d = IpHub.check(args[1]);

                            // api result
                            sender.sendMessage(Warna.color("&7------------------------------"));
                            sender.sendMessage("");
                            sender.sendMessage(Warna.color("&7[DEBUG] result"));
                            sender.sendMessage(Warna.color("&71). ProxyCheck: &6" + a));
                            sender.sendMessage(Warna.color("&72). VpnApi: &6" + b));
                            sender.sendMessage(Warna.color("&73). GetIpIntel: &6" + c));
                            sender.sendMessage(Warna.color("&74). IpHub: &6" + d));
                            sender.sendMessage("");
                            if (!GetIpIntel.isCanUse) {
                                sender.sendMessage(Warna.color("&7[&cERROR&7] &aGetIpIntel: &cError. Replace With ProxyCheck"));
                                sender.sendMessage(Warna.color("&7[&cERROR&7] &aGetIpIntel Message: &c" + GetIpIntel.ErrMsg));
                            } else {
                                sender.sendMessage(Warna.color("&7[7cERROR] &aGetIpIntel error not found"));
                            }

                            if (!ProxyCheck.isCanUse) {
                                sender.sendMessage(Warna.color("&7[&cERROR&7] &aProxyCheck: &cERROR With API"));
                                sender.sendMessage(Warna.color("&7[&cERROR&7] &aProxyCheck Message: &c" + ProxyCheck.ErrMsg));
                            } else {
                                sender.sendMessage(Warna.color("&7[&cERROR&7] &aProxyCheck error not found"));
                            }

                            sender.sendMessage(Warna.color("&7[&cERROR&7] &aVpnApi error not found"));

                            if (!IpHub.isCanUse) {
                                sender.sendMessage(Warna.color("&7[&cERROR7] &aIpHub: &cAre you setup key correctly?, Replace with ProxyCheck"));
                            } else {
                                sender.sendMessage(Warna.color("&7[&cERROR&7] &aIpHub error not found"));
                            }

                            sender.sendMessage("");

                            sender.sendMessage(Warna.color("&7------------------------------"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("info")) {
                if (sender.hasPermission("asantivpn.total")) {
                    sender.sendMessage(Warna.color("&7---------------------------"));
                    sender.sendMessage(Warna.color(""));
                    sender.sendMessage(Warna.color(" &7- &bCurrent"));
                    sender.sendMessage(Warna.color(" &7- &aTotal Join: &6" + NewPrejoin.totalJoin));
                    sender.sendMessage(Warna.color(" &7- &aTotal Blocked: &6" + NewPrejoin.totalBlocked));
                    sender.sendMessage("");
                    sender.sendMessage(Warna.color(" &7- &bStorage"));
                    sender.sendMessage(Warna.color(" &7- &aTotal Join: &6" + SaveData.loadNonVpn()));
                    sender.sendMessage(Warna.color(" &7- &aTotal Blocked: &6" + SaveData.loadVPN()));
                    sender.sendMessage(Warna.color(""));
                    sender.sendMessage(Warna.color("&7---------------------------"));
                }
            } else {
                sender.sendMessage(Warna.color("&7[&eAsAntiVpn&7] &cCommand Not Found!"));
            }
        }
        return false;
    }
}