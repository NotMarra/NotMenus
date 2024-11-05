package com.notmarra.notmenus.utils;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.entity.Player;

import java.util.List;

public class ClickEvents {
    public static void clickEvent(List<String> commands, List<String> messages, Player player) {
        if (commands != null) {
            for (String command : commands) {
                if (command.contains("[close]")) {
                    player.closeInventory();
                } else if (command.contains("[player]")) {
                    String rawCMD = command.replace("[player] ", "");
                    player.performCommand(rawCMD.replace("%player%", player.getName()));
                } else if (command.contains("[console]")) {
                    String rawCMD = command.replace("[console] ", "");
                    NotMenus.getInstance().getServer().dispatchCommand(NotMenus.getInstance().getServer().getConsoleSender(), rawCMD.replace("%player%", player.getName()));
                } else if (command.contains("[refresh]")) {
                    player.updateInventory();
                } else {
                    player.performCommand(command);
                }
            }
        } else if (messages != null) {
            for (String message : messages) {
                player.sendMessage(message);
            }
        }
    }
}
