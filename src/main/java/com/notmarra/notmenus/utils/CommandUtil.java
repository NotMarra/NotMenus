package com.notmarra.notmenus.utils;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandUtil {
    private static CommandMap commandMap;

    public static void registerCommands(Command... commands) {
        if (commandMap == null) {
            setupCommandMap();
        }
        Arrays.stream(commands).forEach(command -> commandMap.register("NotMenus", command));
    }

    private static void setupCommandMap() {
        try {
            PluginManager pluginManager = NotMenus.getInstance().getServer().getPluginManager();
            Field commandMapField = pluginManager.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(pluginManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
