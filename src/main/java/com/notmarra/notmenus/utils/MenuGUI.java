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
    private final int rows;
    private final Map<Character, MenuItem> items = new HashMap<>();

    public MenuGUI(YamlConfiguration config) {
        this.title = config.getString("title", "Menu");
        this.rows = config.getStringList("pattern").size();

        for (String key : config.getConfigurationSection("items").getKeys(false)) {
            Material material = Material.valueOf(config.getString("items." + key + ".material", "STONE"));
            String name = config.getString("items." + key + ".name", "");
            List<String> lore = config.getStringList("items." + key + ".lore");
            items.put(key.charAt(0), new MenuItem(material, name, lore));
        }
    }

    public void open(Player player) {
        GUI ui = Component.gui()
                .title(this.title)
                .rows(this.rows)
                .prepare((gui, p) -> {
                    List<String> pattern = Files.loadFile("menus/testmenu.yml").getStringList("pattern");

                    for (int row = 0; row < pattern.size(); row++) {
                        String line = pattern.get(row);
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
                                                .withName(menuItem.getName())
                                                .withLore(menuItem.getLore())
                                                .build().add())
                                        .build());
                            }
                        }
                    }
                })
                .build();
        ui.open(player);
    }

    private static class MenuItem {
        private final Material material;
        private final String name;
        private final List<String> lore;

        public MenuItem(Material material, String name, List<String> lore) {
            this.material = material;
            this.name = name;
            this.lore = lore;
        }

        public Material getMaterial() {
            return material;
        }

        public String getName() {
            return name;
        }

        public List<String> getLore() {
            return lore;
        }
    }
}

