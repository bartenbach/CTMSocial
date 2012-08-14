package org.hopto.seed419.ctmsocial.file.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.hopto.seed419.CTMSocial;
import org.hopto.seed419.ctmsocial.file.FileHandler;

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
public class BookListener implements Listener {


    private CTMSocial shs;
    private FileHandler fh;
    private Logger log = Logger.getLogger("SHS");


    public BookListener(CTMSocial shs, FileHandler fh) {
        this.shs = shs;
        this.fh = fh;
    }


    @EventHandler
    void onPlayerChest(InventoryOpenEvent event) {
        if (!shs.isEnabledWorld(event.getPlayer().getWorld().getName())) {
            return;
        }

        Player player = (Player) event.getPlayer();
        if (event.getInventory().getType() != null && event.getInventory().getType() == InventoryType.CHEST) {
            Inventory i = event.getInventory();
            for (ItemStack item : i) {
                if (item != null && item.getType() == Material.WRITTEN_BOOK) {
                    //need to verify the title of the book, which currently isn't possible in bukkit :(
                }
            }

        }
    }
}
