package com.notmarra.notmenus.utils;
import com.notmarra.notmenus.NotMenus;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private final JavaPlugin plugin;
    private final Map<String, MenuGUI> menus = new HashMap<>();
    Files filesInstance = new Files();


    public MenuManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadMenus();
    }

    private void loadMenus() {
        String[] menus = filesInstance.getFileNamesFromDirectory("menus");

        for (String menu : menus) {
            plugin.getLogger().info("Loading menu " + menu);
            File file = new File(NotMenus.getInstance().getDataFolder(), "menus/" + menu);
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            this.menus.put(menu.replace(".yml", ""), new MenuGUI(config));
        }
    }

    public void openMenu(Player player, String menuName) {
        MenuGUI menu = menus.get(menuName);
        if (menu != null) {
            menu.open(player);
        } else {
            player.sendMessage("Menu " + menuName + " not found.");
        }
    }

    public void getMenus() {
        for (String menu : menus.keySet()) {
            plugin.getLogger().info(menu);
        }
    }

    public void reloadMenus() {
        menus.clear();
        loadMenus();
    }
}

