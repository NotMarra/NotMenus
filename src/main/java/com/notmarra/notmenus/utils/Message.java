package com.notmarra.notmenus.utils;

import com.notmarra.notmenus.NotMenus;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Message {
    public static String getMessage(String message) {
        return NotMenus.getInstance().getConfig().getString(message);
    }

    public static List<String> getMessageList(String message) {
        return NotMenus.getInstance().getConfig().getStringList(message);
    }

    public static String getPrefix() {
        return NotMenus.getInstance().getConfig().getString("prefix");
    }

    public static void sendMessage(Player player, String message_path, Boolean isConsole, Map<String, String> replacements) {
        String message = getMessage(message_path);
        transformText(player, isConsole, replacements, message);
    }

    public static void sendRawMessage(Player player, String message, Boolean isConsole, Map<String, String> replacements) {
        transformText(player, isConsole, replacements, message);
    }

    private static void transformText(Player player, Boolean isConsole, Map<String, String> replacements, String message) {
        if (message.contains("%prefix%") && !isConsole) {
            message = message.replace("%prefix%", getPrefix());
        } else {
            message = message.replace("%prefix%", "");
        }

        if (replacements != null) {
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                message = message.replace("%" + entry.getKey() + "%", entry.getValue());
            }
        }

        if (player != null && !isConsole) {
            sendChatMessage(player, message);
        }
        if (isConsole) {
            message = stripFormatting(message);
            Bukkit.getLogger().info(message);
        }
    }

    private static String stripFormatting(String input) {
        String withoutMiniMessage = input.replaceAll("<[^>]+>", "");

        return withoutMiniMessage.replaceAll("§[0-9a-fk-or]", "");
    }

    public static void sendChatMessage(Player player, String message) {
        if (message.startsWith("&")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            Audience audience = Audience.audience(player);
            MiniMessage mm = MiniMessage.miniMessage();
            audience.sendMessage(mm.deserialize(message));
        }
    }
}
