package co.proxa.ctmsocial.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import co.proxa.ctmsocial.CTMSocial;
import co.proxa.ctmsocial.Format;
import co.proxa.ctmsocial.file.FileHandler;

public class WoolFindListener implements Listener {


    private CTMSocial shs;
    private FileHandler fh;
    private final String fileName = "/BlocksFound.txt";
    private final String woolGet= ChatColor.GOLD + "[" + ChatColor.YELLOW + "WOOL GET!" + ChatColor.GOLD + "] ";


    public WoolFindListener(CTMSocial shs, FileHandler fh) {
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
                if (item != null && item.getType() == Material.WOOL) {
                    String woolName = Format.getBlockName(item);
                    if (!fh.woolAlreadyInFile(fileName, player.getWorld().getName(), woolName, event.getPlayer().getName())) {
                        broadcastMessage(player, woolName, Format.getBlockColor(item));
                        fh.appendWoolFindToFile(fileName, player.getWorld().getName(), woolName, player.getName());
                    }
                }
            }

        }
    }

    void broadcastMessage(Player player, String blockName, ChatColor color) {
        shs.getServer().broadcastMessage(woolGet);
        shs.getServer().broadcastMessage(player.getDisplayName() + ChatColor.GRAY
                + " found the " + color + blockName + "!");
    }
}
