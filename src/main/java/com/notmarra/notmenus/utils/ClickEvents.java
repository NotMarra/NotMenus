package com.notmarra.notmenus.utils;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.entity.Player;

import java.util.List;

public class ClickEvents {
    public static void clickEvent(List<String> commands, List<String> messages, Player player) {
        for (String command : commands) {
            if (command.contains("[close]")) {
                player.closeInventory();
            } else if (command.contains("[player]")) {
                player.performCommand(command.replace("%player%", player.getName()));
            } else if (command.contains("[console]")) {
                NotMenus.getInstance().getServer().dispatchCommand(NotMenus.getInstance().getServer().getConsoleSender(), command.replace("%player%", player.getName()));
            } else if (command.contains("[refresh]")) {
                player.updateInventory();
            } else {
                player.performCommand(command);
            }
        }

        for (String message : messages) {
            player.sendMessage(message);
        }
    }
}
