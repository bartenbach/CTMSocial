package org.hopto.seed419;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.hopto.seed419.file.Config;
import org.hopto.seed419.file.FileHandler;

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


    private static final String prefix = ChatColor.GOLD + "[Super Hostile Social]";
    private SuperHostileSocial shs;
    private FileHandler fh;


    public Menu(SuperHostileSocial shs, FileHandler fh) {
        this.shs = shs;
        this.fh = fh;
    }

    public static void showMenu(CommandSender sender) {
        sender.sendMessage(prefix + ChatColor.GREEN + " [Commands]");
        sender.sendMessage("    " + ChatColor.RED + "world");
    }

    public static void showWorldMenu(CommandSender sender) {
        sender.sendMessage(prefix + ChatColor.GREEN + " [Commands]");
        sender.sendMessage("    " + ChatColor.RED + "world add [worldname]");
        sender.sendMessage("    " + ChatColor.RED + "world remove [worldname]");
        sender.sendMessage("    " + ChatColor.RED + "world list");
    }

    public void handleWorldMenu(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Menu.showWorldMenu(sender);
        } else {
            String arg = args[1];

            if (arg.equalsIgnoreCase("add")) {
                if (args.length >= 3) {
                    for (World x : shs.getServer().getWorlds()) {
                        if (x.getName().equals(args[2])) {
                            List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
                            if (worlds.contains(args[2])) {
                                sender.sendMessage(ChatColor.RED + args[2] + " is already added as a Super Hostile world!");
                                return;
                            }
                            worlds.add(x.getName());
                            shs.getConfig().set(Config.enabledWorlds, worlds);
                            fh.addWorld(x.getName());
                            sender.sendMessage(ChatColor.GREEN + args[2] + " is now added as a Super Hostile world!");
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
            } else if (arg.equalsIgnoreCase("remove")) {
                if (args.length >= 3) {
                    List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
                    if (worlds.contains(args[2])) {
                        worlds.remove(args[2]);
                        sender.sendMessage(ChatColor.GREEN + args[2] + " has been removed.");
                    } else {
                        sender.sendMessage(ChatColor.RED + args[2] + " isn't currently added as a Super Hostile world.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please supply a world name");
                }
            } else if (arg.equalsIgnoreCase("list")) {
                List<String> worlds = (List<String>) shs.getConfig().getList(Config.enabledWorlds);
                if (worlds.isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "There currently aren't any Super Hostile Social worlds.");
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
}
