package org.hopto.seed419.ctmsocial.file.file;

import org.hopto.seed419.CTMSocial;
import org.hopto.seed419.ctmsocial.file.file.Config;

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


    private CTMSocial ctms;
    private File woolsFound;
    private File woolsPlaced;
    private Logger log = Logger.getLogger("CTMS");


    public FileHandler(CTMSocial ctms)  {
        this.ctms = ctms;
    }

    public void checkFiles() {
        makeSureDirectoryExists();
        ctms.getConfig().options().copyDefaults(true);
        if (ctms.getConfig().getList(Config.enabledWorlds) == null) {
            ctms.getConfig().set(Config.enabledWorlds, new ArrayList<String>());
        }
        ctms.saveConfig();
    }

    public void addWorld(String name) {
        File worldDir = new File(ctms.getDataFolder() + "/" + name);
        if (!worldDir.exists()) {
            worldDir.mkdirs();
        }
        woolsFound = new File(worldDir + "/BlocksFound.txt");
        woolsPlaced = new File(worldDir + "/BlocksPlaced.txt");
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
        if (!ctms.getDataFolder().exists()) {
            ctms.getDataFolder().mkdirs();
        }
    }

    public File verifyFileExists(String world, String fileName) {
        makeSureDirectoryExists();
        File worldDir = new File(ctms.getDataFolder() + "/" + world);
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

    public void appendWoolToFile(String fileName, String world, String blockName, String playerName) {
        File file = verifyFileExists(world, fileName);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(blockName + ":" + playerName);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean woolAlreadyInFile(String fileName, String world, String name, String playerName) {
        File file = verifyFileExists(world, fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String entry;
            while ((entry = br.readLine()) != null) {
                if (entry.split(":")[0].equals(name)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

   public ArrayList<String> getBlocksOnVM(String world) {
       File file = verifyFileExists(world, "/BlocksPlaced.txt");
       ArrayList<String> entries = new ArrayList<String>();
       try {
           BufferedReader br = new BufferedReader(new FileReader(file));
           String entry;
           while ((entry = br.readLine()) != null) {
               entries.add(entry);
           }
           return entries;
       } catch (IOException ex) {
           ex.printStackTrace();
       }
       return null;
   }

    public boolean resetWorld(String world) {
        File file = verifyFileExists(world, "/BlocksPlaced.txt");
        File file2 = verifyFileExists(world, "/BlocksFound.txt");
        boolean delete1 = file.delete();
        boolean delete2 = file2.delete();
        return (delete1 && delete2);
    }
}
