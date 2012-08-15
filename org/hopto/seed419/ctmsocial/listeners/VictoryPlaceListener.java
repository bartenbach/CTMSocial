package org.hopto.seed419.ctmsocial.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.hopto.seed419.CTMSocial;
import org.hopto.seed419.Format;
import org.hopto.seed419.ctmsocial.file.FileHandler;

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
public class VictoryPlaceListener implements Listener {


    private CTMSocial shs;
    private FileHandler fh;
    private String fileName = "/BlocksPlaced.txt";


    public VictoryPlaceListener(CTMSocial shs, FileHandler fh) {
        this.shs = shs;
        this.fh = fh;
    }

    @EventHandler
    void onPlayerWoolPlace(BlockPlaceEvent event) {
        if (!shs.isEnabledWorld(event.getPlayer().getWorld().getName())) {
            return;
        }

        Material m = event.getBlockPlaced().getType();

        if ((m == Material.WOOL || m == Material.IRON_BLOCK || m == Material.GOLD_BLOCK || m == Material.DIAMOND_BLOCK ||
            m == Material.REDSTONE_ORE)
                && event.getBlockAgainst().getType() == Material.GLASS
                && event.getBlock().getRelative(BlockFace.UP).getType() == Material.WALL_SIGN) {

            Sign sign = (Sign) event.getBlock().getRelative(BlockFace.UP).getState();
            String[] text = sign.getLines();
            String line;
            for (String x : text) {
                if (!x.isEmpty()) {
                    line = x;
                    if (line.trim().equals(Format.getBlockName(event.getItemInHand()))) {
                         if (!fh.woolAlreadyInFile(fileName, event.getPlayer().getWorld().getName(), Format.getBlockName(event.getItemInHand()), event.getPlayer().getName())) {
                             shs.getServer().broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GRAY + " placed " +
                                     "the " + Format.getBlockColor(event.getItemInHand()) + Format.getBlockName(event.getItemInHand()) +
                                     ChatColor.GRAY + " on the Victory Monument!");
                             fh.appendVMPlaceToFile(fileName, event.getPlayer().getWorld().getName(), Format.getBlockName(event.getItemInHand()), event.getPlayer().getName(), event.getBlock());
                         }
                    }
                }
            }

         }
    }

}
