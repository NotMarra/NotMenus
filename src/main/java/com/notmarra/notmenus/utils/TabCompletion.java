package com.notmarra.notmenus.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        String currentArg;
        if(args.length == 1) {
            completions.add("reload");
            completions.add("help");
            completions.add("list");
        }
        currentArg = args[args.length - 1];
        String finalCurrentArg = currentArg;
        completions.removeIf((s) -> !s.startsWith(finalCurrentArg));
        return completions;
    }
}
