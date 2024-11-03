package com.notmarra.notmenus;

import com.notmarra.notmenus.utils.Files;
import com.notmarra.notmenus.utils.MenuManager;
import de.tr7zw.changeme.nbtapi.NBT;
import me.zort.containr.Containr;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class NotMenus extends JavaPlugin {
    private static NotMenus instance;
    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        instance = this;
        this.config = this.getConfig();
        this.config.options().copyDefaults(true);
        this.saveDefaultConfig();
        Containr.init(this);
        MenuManager menuManager = new MenuManager(this);

        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Files.createFile("menus/testmenu.yml");
        getCommand("menu").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player player) {
                menuManager.openMenu(player, "testmenu");

                player.sendMessage("You opened the GUI!");

            }
            return true;
        });



        getLogger().info("NotMenus has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NotMenus has been disabled!");
    }

    public static NotMenus getInstance() {
        return instance;
    }
}
