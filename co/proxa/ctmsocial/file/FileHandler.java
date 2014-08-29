package co.proxa.ctmsocial.file;

import co.proxa.ctmsocial.CTMSocial;
import org.bukkit.block.Block;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHandler {


    private CTMSocial ctms;
    private final String blocksFoundFile = "/BlocksFound.txt";
    private final String blocksPlacedFile = "/BlocksPlaced.txt";
    private final String booksFoundFile = "/BooksFound.txt";


    public FileHandler(CTMSocial ctms)  {
        this.ctms = ctms;
    }

    public void checkFiles() {
        verifyDirectoryExists();
        ctms.getConfig().options().copyDefaults(true);
        if (ctms.getConfig().getList(Config.enabledWorlds) == null) {
            ctms.getConfig().set(Config.enabledWorlds, new ArrayList<String>());
        }
        ctms.saveConfig();
    }

    // TODO no delete world?
    // TODO book file management

    public void addWorld(String worldName) {
        File worldDir = new File(ctms.getDataFolder() + "/" + worldName);
        if (!worldDir.exists()) {
            worldDir.mkdirs();
        } else {
            // world already exists...
        }
        File blocksFound = new File(worldDir + blocksFoundFile);
        File blocksPlaced = new File(worldDir + blocksFoundFile);
        File booksFound = new File(worldDir + booksFoundFile);
        if (!blocksFound.exists()) {
            try {
                blocksFound.createNewFile();
            } catch (IOException e) {
                ctms.getLogger().severe("Unable to create BlocksFound.txt");
                e.printStackTrace();
            }
        }
        if (!blocksPlaced.exists()) {
            try {
                blocksPlaced.createNewFile();
            } catch (IOException e) {
                ctms.getLogger().severe("Unable to create BlocksPlaced.txt");
                e.printStackTrace();
            }
        }
        if (!booksFound.exists()) {
            try {
                booksFound.createNewFile();
            } catch (IOException e) {
                ctms.getLogger().severe("Unable to create BooksFound.txt");
                e.printStackTrace();
            }
        }
    }

    public void verifyDirectoryExists() {
        if (!ctms.getDataFolder().exists()) {
            boolean success = ctms.getDataFolder().mkdirs();
            if (success) {
                ctms.getLogger().info("Created new CTMSocial directory.");
            } else {
                ctms.getLogger().severe("Unable to create CTMSocial directory.");
            }
        }
    }

    public File verifyFileExists(String world, String fileName) {
        verifyDirectoryExists();
        File worldDir = new File(ctms.getDataFolder() + "/" + world);
        File file = new File(worldDir + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                ctms.getLogger().severe("Can't create file!");
                e.printStackTrace();
            }
        }
        return file;

    }

    public void appendWoolFindToFile(String fileName, String world, String blockName, String playerName) {
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

    public void appendVMPlaceToFile(String fileName, String world, String blockName, String playerName, Block block) {
        File file = verifyFileExists(world, fileName);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println(blockName + ":" + playerName + ":" + block.getX() + "," + block.getY() + "," + block.getZ());
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
       File file = verifyFileExists(world, blocksPlacedFile);
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
        File file = verifyFileExists(world, blocksPlacedFile);
        File file2 = verifyFileExists(world, blocksFoundFile);
        File file3 = verifyFileExists(world, booksFoundFile);
        boolean delete1 = file.delete();
        boolean delete2 = file2.delete();
        boolean delete3 = file3.delete();
        return (delete1 && delete2);
    }

    public boolean deleteWorld(String worldName) {
        boolean success = false;
        try {
            Files.deleteIfExists(Paths.get(worldName, booksFoundFile));
        } catch (Exception ex) {
            ctms.getLogger().severe("Unable to delete BooksFound.txt");
        }
        try {
            Files.deleteIfExists(Paths.get(worldName, blocksPlacedFile));
        } catch (Exception ex) {
            ctms.getLogger().severe("Unable to delete BlocksPlaced.txt");
        }
        try {
            Files.deleteIfExists(Paths.get(worldName, blocksFoundFile));
        } catch (Exception ex) {
            ctms.getLogger().severe("Unable to delete BlocksFound.txt");
        }
        try {
            success = Files.deleteIfExists(Paths.get(worldName));
        } catch (Exception ex) {
            ctms.getLogger().severe("Unable to delete BooksFound.txt");
        }
        return success;
    }

    // TODO what in the actual fuck is this?
/*    public HashSet<Location> loadVMBlockLocations() {
        HashSet<Location> locations = new HashSet<Location>();
        File dir = ctms.getDataFolder();
        List<World> worlds = ctms.getServer().getWorlds();
        for (World w : worlds) {
            System.out.println(w.getName());
            if (ctms.getConfig().getList(Config.enabledWorlds).contains(w.getName())) {
                for (String f : dir.list()) {
                    System.out.println("Dirlist: f");
                    if (f.equals(w.getName())) {
                        System.out.println("match!" + f);
                    }
                }
            }
        }
        return locations;
    }*/
}
