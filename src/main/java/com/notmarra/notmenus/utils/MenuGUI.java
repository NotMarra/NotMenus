package com.notmarra.notmenus.utils;

import me.zort.containr.Component;
import me.zort.containr.GUI;
import me.zort.containr.internal.util.ItemBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuGUI {
    private final String title;
    private final String command;
    private final String permission;
    private final String permission_message;
    private final String description;
    private final String aliases;
    private final List<String> pattern;
    private final int rows;

    private final Map<Character, MenuItem> items = new HashMap<>();

    public MenuGUI(YamlConfiguration config) {
        this.title = config.getString("title", "Menu");
        this.command = config.getString("command", "notmenus open " + this.title);
        if (config.contains("permission")) {
            this.permission = config.getString("permission");
        } else {
            this.permission = null;
        }
        this.permission_message = config.getString("permission_message", "You do not have permission to use this command.");
        this.description = config.getString("description", "Open the " + this.title + " menu");
        this.aliases = config.getString("aliases", "");
        this.pattern = config.getStringList("pattern");
        this.rows = pattern.size();

        for (String key : config.getConfigurationSection("items").getKeys(false)) {
            Material material = Material.valueOf(config.getString("items." + key + ".material", "STONE"));
            String name = config.getString("items." + key + ".name", "");
            int amount = config.getInt("items." + key + ".amount", 1);
            List<String> lore = config.getStringList("items." + key + ".lore");
            items.put(key.charAt(0), new MenuItem(material, name, amount, lore));
        }
    }

    public void open(Player player) {
        GUI ui = Component.gui()
                .title(this.title)
                .rows(this.rows)
                .prepare((gui, p) -> {

                    for (int row = 0; row < this.pattern.size(); row++) {
                        String line = this.pattern.get(row);
                        for (int col = 0; col < line.length(); col++) {
                            char symbol = line.charAt(col);
                            MenuItem menuItem = items.get(symbol);
                            if (menuItem != null) {
                                gui.setElement(row * 9 + col, Component.element()
                                        .click(info -> {
                                            Player whoClicked = info.getPlayer();
                                            whoClicked.sendMessage("Clicked on " + menuItem.getName());
                                        })
                                        .item(new ItemBuilder()
                                                .withType(menuItem.getMaterial())
                                                .withAmount(menuItem.getAmount())
                                                .withName(menuItem.getName())
                                                .withLore(menuItem.getLore())
                                                .build())
                                        .build());
                            }
                        }
                    }
                })
                .build();
        ui.open(player);
    }

    public String getCommand() {
        return command;
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionMessage() {
        return permission_message;
    }

    public String getDescription() {
        return description;
    }

    public String getAliases() {
        return aliases;
    }

    private static class MenuItem {
        private final Material material;
        private final int amount;
        private final String name;
        private final List<String> lore;

        public MenuItem(Material material, String name, int amount, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.name = name;
            this.lore = lore;
        }

        public Material getMaterial() {
            return material;
        }

        public int getAmount() {
            return amount;
        }

        public String getName() {
            return name;
        }

        public List<String> getLore() {
            return lore;
        }
    }
}

