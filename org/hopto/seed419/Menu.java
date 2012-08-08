package org.hopto.seed419;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hopto.seed419.file.Config;
import org.hopto.seed419.file.FileHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Attribute Only (Public) License
 * Version 0.a3, July 11, 2011
 * <p/>
 * Copyright (C) 2012 Blake Bartenbach <seed419@gmail.com> (@seed419)
 * <p/>
 * Anyone is allowed to copy and distribute verbatim or modified
 * copies of this license document and altering is allowed as long
 * as you attribute the author(s) of this license document / files.
 * <p/>
 * ATTRIBUTE ONLY PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * <p/>
 * 1. Attribute anyone attached to the license document.
 * Do not remove pre-existing attributes.
 * <p/>
 * Plausible attribution methods:
 * 1. Through comment blocks.
 * 2. Referencing on a site, wiki, or about page.
 * <p/>
 * 2. Do whatever you want as long as you don't invalidate 1.
 *
 * @license AOL v.a3 <http://aol.nexua.org>
 */
public class Menu {


    private static final String prefix = ChatColor.GOLD + "[CTM Social]";
    private CTMSocial shs;
    private FileHandler fh;


    public Menu(CTMSocial shs, FileHandler fh) {
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

    public void handleWorldMenu(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Menu.showWorldMenu(sender);
        } else {
            String arg = args[1];

            if (arg.equalsIgnoreCase("enable")) {
                if (args.length >= 3) {
                    for (World x : shs.getServer().getWorlds()) {
                        if (x.getName().equals(getArgs(args))) {
                            List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
                            if (worlds.contains(args[2])) {
                                sender.sendMessage(ChatColor.RED + args[2] + " is already added as a CTM world!");
                                return;
                            }
                            worlds.add(x.getName());
                            shs.getConfig().set(Config.enabledWorlds, worlds);
                            fh.addWorld(x.getName());
                            sender.sendMessage(ChatColor.GREEN + args[2] + " is now enabled as a CTM world");
                            shs.saveConfig();
                            return;
                        }
                    }
                    sender.sendMessage(Menu.getPrefix());
                    sender.sendMessage(ChatColor.RED + args[2] + " is not a loaded world");
                    sender.sendMessage(ChatColor.RED + "World names are case sensitive!");
                    return;
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            } else if (arg.equalsIgnoreCase("disable")) {
                if (args.length >= 3) {
                    List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
                    if (worlds.contains(getArgs(args))) {
                        worlds.remove(args[2]);
                        sender.sendMessage(ChatColor.GREEN + args[2] + " has been disabled.");
                    } else {
                        sender.sendMessage(ChatColor.RED + args[2] + " isn't currently enabled as a CTM world.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            } else if (arg.equalsIgnoreCase("reset")) {
                if (args.length >= 3) {
                   List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
                    if (worlds.contains(getArgs(args))) {
                        if (fh.resetWorld(getArgs(args))) {
                            final CommandSender senderf = sender;
                            final String theargs = getArgs(args);
                            sender.sendMessage(ChatColor.DARK_RED + "Resetting wool found in chests...");
                            sender.sendMessage(ChatColor.DARK_RED + "Resetting Victory Monument...");
                            shs.getServer().getScheduler().scheduleAsyncDelayedTask(shs, new Runnable() {
                                @Override
                                public void run() {
                                    senderf.sendMessage(ChatColor.GREEN + theargs + " has been reset.");
                                }
                            }, 20L);
                        } else {
                            sender.sendMessage(ChatColor.RED + getArgs(args) + " could not be reset.");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            } else if (arg.equalsIgnoreCase("list")) {
                List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
                if (worlds.isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "There currently aren't any enabled CTM worlds.");
                } else {
                    sender.sendMessage(Menu.getPrefix() + ChatColor.GREEN + " [Enabled Worlds]");
                    for (String x : worlds) {
                        sender.sendMessage(x);
                    }
                }
            }
        }
    }

    public static String getPrefix() {
        return prefix;
    }

    public void handleVMList(Player player) {
        String world = player.getWorld().getName();
        if (shs.isEnabledWorld(world)) {
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + world + ChatColor.GOLD + "] " +
                    ChatColor.GOLD + "[Victory Monument Status]");
            ArrayList<String> entries = fh.getBlocksOnVM(world);
            if (entries != null && entries.size() > 0) {
                for (String x : entries) {
                    String[] split = x.split(":");
                    player.sendMessage(Format.getBlockColor(split[0]) + split[0] + ChatColor.GRAY + " found by "
                            + ChatColor.AQUA + ChatColor.ITALIC + split[1]);
                }
            } else {
                player.sendMessage(ChatColor.RED + "No blocks have been placed on the Victory Monument yet.");
            }
        }
    }

    public String getArgs(String[] args) {
        StringBuilder sb = new StringBuilder();
        args[0] = "$";
        args[1] = "$";
        for (String x : args) {
            if (!x.equals("$")) {
                sb.append(x).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
