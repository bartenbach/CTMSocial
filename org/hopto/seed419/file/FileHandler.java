package org.hopto.seed419.file;

import org.hopto.seed419.SuperHostileSocial;

import java.io.*;
import java.util.ArrayList;
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
public class FileHandler {


    private SuperHostileSocial shwa;
    private File woolsFound;
    private File woolsPlaced;
    private Logger log = Logger.getLogger("SHA");


    public FileHandler(SuperHostileSocial shwa) {
        this.shwa = shwa;
    }

    public void checkFiles() {
        makeSureDirectoryExists();
        shwa.getConfig().options().copyDefaults(true);
        if (shwa.getConfig().getList(Config.enabledWorlds) == null) {
            shwa.getConfig().set(Config.enabledWorlds, new ArrayList<String>());
        }
        shwa.saveConfig();
    }

    public void addWorld(String name) {
        File worldDir = new File(shwa.getDataFolder() + "/" + name);
        if (!worldDir.exists()) {
            worldDir.mkdirs();
        }
        woolsFound = new File(worldDir + "/.woolsFound");
        woolsPlaced = new File(worldDir + "/.woolsPlaced");
        if (!woolsFound.exists()) {
            try {
                woolsFound.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!woolsPlaced.exists()) {
            try {
                woolsPlaced.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeSureDirectoryExists() {
        if (!shwa.getDataFolder().exists()) {
            shwa.getDataFolder().mkdirs();
        }
    }

    public File verifyFileExists(String world, String fileName) {
        makeSureDirectoryExists();
        File worldDir = new File(shwa.getDataFolder() + "/" + world);
        File file = new File(worldDir + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                log.severe("Can't create file!");
                e.printStackTrace();
            }
        }
        return file;

    }

    public void appendWoolToFile(String fileName, String world, String name) {
        File file = verifyFileExists(world, fileName);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(name);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean woolAlreadyInFile(String fileName, String world, String name) {
        File file = verifyFileExists(world, fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String wool;
            while ((wool = br.readLine()) != null) {
                if (wool.equals(name)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
