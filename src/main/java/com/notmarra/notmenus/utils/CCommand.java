package com.notmarra.notmenus.utils;
import java.util.Arrays;

import com.notmarra.notmenus.NotMenus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
@SuppressWarnings("all")
public abstract class CCommand extends Command implements PluginIdentifiableCommand{
    CommandSender sender;//So you can mess with this inside this class
   NotMenus plugin = NotMenus.getInstance();
    protected CCommand(String name) {
        super(name);
    }

    @Override//Sets the plugin to our plugin so it shows up in /help
    public Plugin getPlugin() {
        return plugin;
    }

    public abstract void run(CommandSender sender, String commandLabel, String[] arguments);//Just simpler and allows 'return;' instead of 'return true/false;'

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        this.sender = sender;
        run(sender, commandLabel, arguments);//actually run the command.
        return true;
    }

    protected void sendMessage(String... messages) {
        Arrays.stream(messages)
                .forEach(message -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));//Why not have a way to send messages easily colored?
    }
    protected void sendMessage(CommandSender sender, String... messages) {
        Arrays.stream(messages)
                .forEach(message -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));//Why not have a way to send messages easily colored?
    }
}