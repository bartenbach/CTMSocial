package co.proxa.ctmsocial.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import co.proxa.ctmsocial.CTMSocial;
import co.proxa.ctmsocial.Format;
import co.proxa.ctmsocial.file.FileHandler;

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
