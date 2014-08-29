package co.proxa.ctmsocial.handler;

import co.proxa.ctmsocial.CTMSocial;
import co.proxa.ctmsocial.file.Config;

import java.util.List;

public class WorldHandler {

    private static CTMSocial ctms;

    public WorldHandler(CTMSocial ctm) {
        ctms = ctm;
    }


    public static boolean disableWorld(String worldName) {
        List<String> worlds = (List<String>) ctms.getConfig().getList(Config.enabledWorlds);
        return worlds.remove(worldName);
    }

    public static boolean isEnabledWorld(String worldName) {
        for (String x : (List<String>) ctms.getConfig().getList(Config.enabledWorlds)) {
            if (x.equals(worldName)) {
                return true;
            }
        }
        return false;
    }
}
