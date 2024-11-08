package com.notmarra.notmenus.cmds;

import com.notmarra.notmenus.NotMenus;
import com.notmarra.notmenus.utils.MenuGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class List {
    public static void execute(CommandSender sender) {
        if (sender instanceof Player p) {
            if (!p.hasPermission("notmenus.list")) {
                p.sendMessage("You do not have permission to use this command.");
            }

            Map<String, MenuGUI> menus = NotMenus.getInstance().getMenuManager().getMenus();

            if (menus.isEmpty()) {
                p.sendMessage("There are no menus available.");
            } else {
                p.sendMessage("Available menus:");
                for (String menu : menus.keySet()) {
                    p.sendMessage(menu);
                }
            }
        }
    }
}
