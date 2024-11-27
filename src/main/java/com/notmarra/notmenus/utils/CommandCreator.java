package com.notmarra.notmenus.utils;

import com.notmarra.notmenus.cmds.Help;
import com.notmarra.notmenus.cmds.List;
import com.notmarra.notmenus.cmds.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCreator implements CommandExecutor {
    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Help.execute(sender);
        }

        if (args.length >= 1) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "reload":
                    Reload.execute(sender);
                    break;
                case "list":
                    List.execute(sender);
                    break;
                default:
                    Help.execute(sender);
                    break;
            }
        }
        return true;
    }

}