package co.proxa.ctmsocial;

import co.proxa.ctmsocial.file.Config;
import co.proxa.ctmsocial.file.FileHandler;
import co.proxa.ctmsocial.handler.CommandHandler;
import co.proxa.ctmsocial.handler.MenuHandler;
import co.proxa.ctmsocial.handler.WorldHandler;
import co.proxa.ctmsocial.listeners.BookListener;
import co.proxa.ctmsocial.listeners.VictoryPlaceListener;
import co.proxa.ctmsocial.listeners.WoolFindListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CTMSocial extends JavaPlugin {

    /* Changelog:
     *  Fixed the new world delete command
     *  Implemented books!
     */

    private final FileHandler fh = new FileHandler(this);
    private final MenuHandler menu = new MenuHandler(this, fh);
    private final WorldHandler wh = new WorldHandler(this);
    private final CommandHandler ch = new CommandHandler();

    @Override
    public void onEnable() {
        fh.checkFiles();
        //vmBlocks = fh.loadVMBlockLocations();
        registerEnabledListeners();
    }

    private void registerEnabledListeners() {
        PluginManager pm = getServer().getPluginManager();
        if (this.getConfig().getBoolean(Config.announceVMPlacement)) {
            pm.registerEvents(new VictoryPlaceListener(this, fh), this);
        }
        if (this.getConfig().getBoolean(Config.announceWoolFinds)) {
            pm.registerEvents(new WoolFindListener(this, fh), this);
        }
        if (this.getConfig().getBoolean(Config.announceBookFinds)) {
            pm.registerEvents(new BookListener(this, fh), this);
        }
        this.getCommand("vm").setExecutor(ch);
        this.getCommand("ctm").setExecutor(ch);
        this.getCommand("books").setExecutor(ch);
    }

}
