package com.notmarra.notmenus;

import com.notmarra.notmenus.cmds.Reload;
import com.notmarra.notmenus.utils.CCommand;
import com.notmarra.notmenus.utils.CommandUtil;
import com.notmarra.notmenus.utils.Files;
import com.notmarra.notmenus.utils.MenuManager;
import de.tr7zw.changeme.nbtapi.NBT;
import me.zort.containr.Containr;
import org.bukkit.command.CommandSender;
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

        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Files.createFile("menus/testmenu.yml");
        menuManager = new MenuManager(this);

        getLogger().info("Registering commands!");
        menuManager.registerCommands();

        CommandUtil.registerCommands(new CCommand("reload", "Reload the plugin") {
            @Override
            public void run(CommandSender sender, String commandLabel, String[] arguments) {
                Reload.execute(sender);
            }
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
}
