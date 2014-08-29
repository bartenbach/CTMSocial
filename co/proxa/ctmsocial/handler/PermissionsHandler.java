package co.proxa.ctmsocial.handler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PermissionsHandler {

    public static boolean hasPerms(CommandSender sender) {
        return (sender.hasPermission("ctm.*") || sender.isOp());
    }

    public static void sendPermissionsMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You don't have sufficient permission.");
    }
}
