package com.notmarra.notmenus.cmds;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload {
    public static void execute(CommandSender sender) {
        if (sender instanceof Player p) {
            if (p.hasPermission("notcredits.reload")) {
                NotMenus.getInstance().reload();
                p.sendMessage("NotMenus has been reloaded.");
            } else {
                p.sendMessage("You do not have permission to use this command.");
            }
        } else {
            NotMenus.getInstance().reload();
        }
    }
}
