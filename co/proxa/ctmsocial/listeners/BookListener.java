package co.proxa.ctmsocial.listeners;

import co.proxa.ctmsocial.CTMSocial;
import co.proxa.ctmsocial.file.FileHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookListener implements Listener {


    private CTMSocial shs;
    private FileHandler fh;
    private final String fileName = "/BooksFound.txt";
    private final String bookGet= ChatColor.GOLD + "[" + ChatColor.YELLOW + "BOOK GET!" + ChatColor.GOLD + "] ";


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
                    BookMeta bm = (BookMeta) item.getItemMeta();
                    String bookTitle = bm.getTitle();
                    broadcastMessage(player, bookTitle);
                }
            }

        }
    }

    void broadcastMessage(Player player, String title) {
        shs.getServer().broadcastMessage(bookGet);
        shs.getServer().broadcastMessage(player.getDisplayName() + ChatColor.GRAY
                + " found " + ChatColor.DARK_AQUA + title + "!");
    }
}
