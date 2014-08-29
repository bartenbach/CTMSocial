package co.proxa.ctmsocial.handler;

import co.proxa.ctmsocial.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ctm")) {
            if (args.length == 0) {
                MenuHandler.showMenu(sender);
            } else  {
                String arg = args[0];
                if (arg.equalsIgnoreCase("world")) {
                    if (PermissionsHandler.hasPerms(sender)) {
                        MenuHandler.handleWorldMenu(sender, args);
                    } else {
                        PermissionsHandler.sendPermissionsMessage(sender);
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Unrecognized argument.  See " + ChatColor.GOLD + "/ctm "
                            + ChatColor.RED + "for help");
                }
            }
        } else if (label.equalsIgnoreCase("vm")) {
            if (!(sender instanceof Player)) {
                if (args.length >= 1) {
                    MenuHandler.handleVMList(sender, StringUtils.getArgsAll(args));
                } else {
                    sender.sendMessage(ChatColor.RED + "Console Usage: " + ChatColor.GOLD + "/vm <worldname>");
                }
            } else {
                MenuHandler.handleVMList((Player)sender);
            }
        } else if (label.equalsIgnoreCase("books")) {
            if (!(sender instanceof Player)) {
                if (args.length >= 1) {
                    MenuHandler.handleBookList(sender, StringUtils.getArgsAll(args));
                } else {
                    sender.sendMessage(ChatColor.RED + "Console Usage: " + ChatColor.GOLD + "/books <worldname>");
                }
            } else {
                MenuHandler.handleBookList((Player) sender);
            }        }
        return false;
    }

}
