package com.notmarra.notmenus.utils;

import me.zort.containr.Component;
import me.zort.containr.GUI;
import me.zort.containr.internal.util.ItemBuilder;
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
            Map<String, List<String>> clickEvents = new HashMap<>();
            if (config.contains("items." + key + ".click_events")) {
                for (String eventType : config.getConfigurationSection("items." + key + ".click_events").getKeys(false)) {
                    List<String> commands = config.getStringList("items." + key + ".click_events." + eventType + ".commands");
                    List<String> messages = config.getStringList("items." + key + ".click_events." + eventType + ".messages");
                    clickEvents.put(eventType + "_commands", commands);
                    clickEvents.put(eventType + "_messages", messages);
                }
            }
            items.put(key.charAt(0), new MenuItem(material, name, amount, lore, clickEvents));
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
                                            if (info.getClickType().isLeftClick()) {
                                                List<String> commands = menuItem.clickEvents().get("left_click_commands");
                                                List<String> messages = menuItem.clickEvents().get("left_click_messages");
                                                ClickEvents.clickEvent(commands, messages, whoClicked);
                                            } else if (info.getClickType().isRightClick()) {
                                                List<String> commands = menuItem.clickEvents().get("right_click_commands");
                                                List<String> messages = menuItem.clickEvents().get("right_click_messages");
                                                ClickEvents.clickEvent(commands, messages, whoClicked);
                                            }
                                        })
                                        .item(new ItemBuilder()
                                                .withType(menuItem.material())
                                                .withAmount(menuItem.amount())
                                                .withName(menuItem.name())
                                                .withLore(menuItem.lore())
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

    private record MenuItem(Material material, String name, int amount, List<String> lore,
                            Map<String, List<String>> clickEvents) {
    }
}

