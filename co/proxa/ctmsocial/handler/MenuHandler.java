package co.proxa.ctmsocial.handler;

import co.proxa.ctmsocial.CTMSocial;
import co.proxa.ctmsocial.Format;
import co.proxa.ctmsocial.file.Config;
import co.proxa.ctmsocial.file.FileHandler;
import co.proxa.ctmsocial.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler {


    private static final String prefix = ChatColor.GOLD + "[CTM Social]";
    private static CTMSocial shs;
    private static FileHandler fh;


    public MenuHandler(CTMSocial shs, FileHandler fh) {
        this.shs = shs;
        this.fh = fh;
    }

    public static void showMenu(CommandSender sender) {
        sender.sendMessage(prefix + ChatColor.GREEN + " [Commands]");
        sender.sendMessage("    " + ChatColor.RED + "world {enable, disable, reset, list}");
        sender.sendMessage("    " + ChatColor.RED + "vm");
    }

    public static void showWorldMenu(CommandSender sender) {
        sender.sendMessage(prefix + ChatColor.GREEN + " [Commands]");
        sender.sendMessage("    " + ChatColor.RED + "world enable [worldname]");
        sender.sendMessage("    " + ChatColor.RED + "world disable [worldname]");
        sender.sendMessage("    " + ChatColor.RED + "world reset [worldname]");
        sender.sendMessage("    " + ChatColor.RED + "world list");
    }

    public static void handleWorldMenu(CommandSender sender, String[] args) {
        if (args.length == 1) {
            MenuHandler.showWorldMenu(sender);
        } else {
            String arg = args[1];
            List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
            if (arg.equalsIgnoreCase("enable")) {
                if (args.length >= 3) {
                    String world = StringUtils.getArgs(args);
                    for (World x : shs.getServer().getWorlds()) {
                        if (x.getName().equals(world)) {
                            if (worlds.contains(world)) {
                                sender.sendMessage(ChatColor.RED + world + " is already added as a CTM world!");
                                return;
                            }
                            worlds.add(x.getName());
                            shs.getConfig().set(Config.enabledWorlds, worlds);
                            fh.addWorld(x.getName());
                            sender.sendMessage(ChatColor.GREEN + world + " is now enabled as a CTM world");
                            shs.saveConfig();
                            return;
                        }
                    }
                    sender.sendMessage(MenuHandler.getPrefix());
                    sender.sendMessage(ChatColor.RED + world + " is not a loaded world");
                    sender.sendMessage(ChatColor.RED + "World names are case sensitive!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            } else if (arg.equalsIgnoreCase("disable")) {
                if (args.length >= 3) {
                    String world = StringUtils.getArgs(args);
                    if (worlds.contains(world)) {
                        worlds.remove(world);
                        sender.sendMessage(ChatColor.GREEN + args[2] + " has been disabled.");
                    } else {
                        sender.sendMessage(ChatColor.RED + args[2] + " isn't currently enabled as a CTM world.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            } else if (arg.equalsIgnoreCase("reset")) {
                if (args.length >= 3) {
                    String world = StringUtils.getArgs(args);
                    if (worlds.contains(world)) {
                        if (fh.resetWorld(world)) {
                            sender.sendMessage(ChatColor.GREEN + world + " has been reset.");
                        } else {
                            sender.sendMessage(ChatColor.RED + StringUtils.getArgs(args) + " could not be reset.");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            } else if (arg.equalsIgnoreCase("list")) {
                if (worlds.isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "There currently aren't any enabled CTM worlds.");
                } else {
                    sender.sendMessage(MenuHandler.getPrefix() + ChatColor.GREEN + " [Enabled Worlds]");
                    for (String x : worlds) {
                        sender.sendMessage(x);
                    }
                }
            } else if (arg.equalsIgnoreCase("delete")) {
                if (args.length >= 3) {
                    String worldName = StringUtils.getArgs(args);
                    if (worlds.contains(worldName)) {
                        sender.sendMessage(ChatColor.DARK_RED + "Deleting data...");
                        if (fh.deleteWorld(worldName)) {
                            sender.sendMessage(ChatColor.GREEN + worldName + " has been deleted.");
                        } else {
                            sender.sendMessage(ChatColor.GOLD + worldName + ChatColor.RED + " could not be deleted.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "World " + worldName + " not found.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            }else {
                sender.sendMessage(ChatColor.RED + "Unrecognized argument.  See " + ChatColor.GOLD + "/ctm "
                        + ChatColor.RED + "for help");
            }
        }
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void handleVMList(Player player) {
        String world = player.getWorld().getName();
        validateWorld(player, world);
    }

    public static void handleVMList(CommandSender sender, String worldName) {
        try {
            String world = shs.getServer().getWorld(worldName).getName();
            validateWorld(sender, world);
        } catch (NullPointerException ex) {
            sender.sendMessage(ChatColor.RED + "No data found for world with name: " + ChatColor.GOLD + worldName);
        }
    }

    public static void validateWorld(CommandSender sender, String worldName) {
        if (shs.isEnabledWorld(worldName)) {
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + worldName + ChatColor.GOLD + "] " +
                    ChatColor.GOLD + "[Victory Monument Status]");
            ArrayList<String> entries = fh.getBlocksOnVM(worldName);
            if (entries != null && entries.size() > 0) {
                for (String x : entries) {
                    String[] split = x.split(":");
                    sender.sendMessage(Format.getBlockColor(split[0]) + split[0] + ChatColor.GRAY + " found by "
                            + ChatColor.AQUA + ChatColor.ITALIC + split[1]);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No blocks have been placed on the Victory Monument yet.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "No data found for world with name " + ChatColor.GOLD + worldName);
        }
    }
}
