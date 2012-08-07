package org.hopto.seed419;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

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
public class Format {


    private static Logger log = Logger.getLogger("SHS");


    public static String getBlockName(ItemStack item) {
        if (item.getType() == Material.WOOL) {
            switch (item.getData().getData()) {
                case 0:
                    return "White Wool";
                case 1:
                    return "Orange Wool";
                case 2:
                    return "Magenta Wool";
                case 3:
                    return "Light Blue Wool";
                case 4:
                    return "Yellow Wool";
                case 5:
                    return "Lime Wool";
                case 6:
                    return "Pink Wool";
                case 7:
                    return "Gray Wool";
                case 8:
                    return "Light Gray Wool";
                case 9:
                    return "Cyan Wool";
                case 10:
                    return "Purple Wool";
                case 11:
                    return "Blue Wool";
                case 12:
                    return "Brown Wool";
                case 13:
                    return "Green Wool";
                case 14:
                    return "Red Wool";
                case 15:
                    return "Black Wool";
                default:
                    return "Unknown Wool";
                }
        } else {
            switch (item.getType()) {
                case GOLD_BLOCK:
                    return "Gold Block";
                case DIAMOND_BLOCK:
                    return "Diamond Block";
                case IRON_BLOCK:
                    return "Iron Block";
                case REDSTONE_ORE:
                    return "Redstone Ore";
            }
        }
        log.severe("Unrecognized Block, please report this.");
        return "Unknown";
    }

    public static ChatColor getBlockColor(ItemStack item) {
        if (item.getType() == Material.WOOL) {
            switch (item.getData().getData()) {
                case 0:
                    return ChatColor.WHITE;
                case 1:
                    return ChatColor.GOLD;
                case 2:
                    return ChatColor.DARK_PURPLE;
                case 3:
                    return ChatColor.BLUE;
                case 4:
                    return ChatColor.YELLOW;
                case 5:
                    return ChatColor.GREEN;
                case 6:
                    return ChatColor.LIGHT_PURPLE;
                case 7:
                    return ChatColor.DARK_GRAY;
                case 8:
                    return ChatColor.GRAY;
                case 9:
                    return ChatColor.DARK_AQUA;
                case 10:
                    return ChatColor.DARK_PURPLE;
                case 11:
                    return ChatColor.DARK_BLUE;
                case 12:
                    return ChatColor.DARK_GRAY;
                case 13:
                    return ChatColor.DARK_GREEN;
                case 14:
                    return ChatColor.RED;
                case 15:
                    return ChatColor.BLACK;
                default:
                    return ChatColor.WHITE;
            }
        } else {
            switch (item.getType()) {
                case REDSTONE_ORE:
                    return ChatColor.DARK_RED;
                case GOLD_BLOCK:
                    return ChatColor.GOLD;
                case IRON_BLOCK:
                    return ChatColor.GRAY;
                case DIAMOND_BLOCK:
                    return ChatColor.AQUA;
            }
        }
        log.severe("Unrecognized Block, please report this.");
        return null;
    }

}
