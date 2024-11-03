package com.notmarra.notmenus.utils;
import java.util.Arrays;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public abstract class CCommand extends Command implements PluginIdentifiableCommand{
    CommandSender sender;
    NotMenus plugin = NotMenus.getInstance();
    private String pm;

    protected CCommand(String name, String description, String permission, String permission_message, String... aliases) {
        super(name);
        setDescription(description);
        if (permission != null) {
            setPermission(permission);
            pm = permission_message;
        }
        setAliases(Arrays.asList(aliases));
    }


    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public abstract void run(CommandSender sender, String commandLabel, String[] arguments);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        this.sender = sender;

        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            if (pm != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pm));
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            }
            return true;
        }

        run(sender, commandLabel, arguments);
        return true;
    }

    protected void sendMessage(String... messages) {
        Arrays.stream(messages)
                .forEach(message -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
    }

    protected void sendMessage(CommandSender sender, String... messages) {
        Arrays.stream(messages)
                .forEach(message -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
    }
}