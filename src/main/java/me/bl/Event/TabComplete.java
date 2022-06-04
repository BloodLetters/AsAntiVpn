package me.bl.Event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            final List<String> arguments = new ArrayList<String>();
            arguments.add("Reload");
            arguments.add("add");
            arguments.add("remove");
            arguments.add("help");
            return arguments;
        }
        return null;
    }
}
