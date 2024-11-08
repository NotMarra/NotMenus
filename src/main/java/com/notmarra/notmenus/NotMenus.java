package com.notmarra.notmenus;

import com.notmarra.notmenus.utils.*;
import de.tr7zw.changeme.nbtapi.NBT;
import me.zort.containr.Containr;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public final class NotMenus extends JavaPlugin {
    private static NotMenus instance;
    FileConfiguration config = this.getConfig();
    MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        this.config = this.getConfig();
        this.config.options().copyDefaults(true);
        this.saveDefaultConfig();
        Containr.init(this);
        menuManager = new MenuManager(this);


        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Files.createFile("menus/testmenu.yml");
        menuManager.loadMenus();

        getLogger().info("Registering commands!");
        menuManager.registerCommands();

        this.getCommand("notmenus").setExecutor(new CommandCreator());
        this.getCommand("nm").setExecutor(new CommandCreator());
        this.getCommand("notmenus").setTabCompleter(new TabCompletion());
        this.getCommand("nm").setTabCompleter(new TabCompletion());

        getLogger().info("NotMenus has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NotMenus has been disabled!");
    }

    public static NotMenus getInstance() {
        return instance;
    }

    public void reload() {
        this.reloadConfig();
        this.config = this.getConfig();
        if (menuManager != null) {
            menuManager.reloadMenus();
        } else {
            menuManager = new MenuManager(this);
        }
        this.getLogger().info("Plugin reloaded successfully!");
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
