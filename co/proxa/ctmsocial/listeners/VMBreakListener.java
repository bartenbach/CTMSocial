package co.proxa.ctmsocial.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import co.proxa.ctmsocial.CTMSocial;

public class VMBreakListener implements Listener {


    private CTMSocial ctms;


    public VMBreakListener(CTMSocial ctms) {
        this.ctms = ctms;
    }

    // why would i conceivably care about this?  hmm...what was i thinking?
    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        if (ctms.getVmBlocks().contains(event.getBlock().getLocation())) {
            System.out.println("Hey, it's been removed!");
        }
    }
}
