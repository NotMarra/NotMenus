package com.notmarra.notmenus.utils;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.entity.Player;

import java.util.List;

public class ClickEvents {
    public static void clickEvent(List<String> commands, List<String> messages, Player player) {
        if (commands != null) {
            String rawCMD;
            for (String command : commands) {
                switch (command) {
                    case "[close]":
                        player.closeInventory();
                        break;
                    case "[player]":
                        rawCMD = command.replace("[player] ", "");
                        player.performCommand(rawCMD.replace("%player%", player.getName()));
                        break;
                    case "[console]":
                        rawCMD = command.replace("[console] ", "");
                        NotMenus.getInstance().getServer().dispatchCommand(NotMenus.getInstance().getServer().getConsoleSender(), rawCMD.replace("%player%", player.getName()));
                        break;
                    case "[refresh]":
                        player.updateInventory();
                        break;
                    default:
                        NotMenus.getInstance().getLogger().severe("Invalid menu command: " + command);
                        break;
                }
            }
        } else if (messages != null) {
            for (String message : messages) {
                player.sendMessage(message);
            }
        }
    }
}
