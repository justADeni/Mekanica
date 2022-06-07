package justadeni.mekanica.utils.files;

import com.google.gson.Gson;
import justadeni.mekanica.Mekanica;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.LocHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.*;
import java.nio.file.Path;

import java.util.*;


public class Storage {

    private static final HashMap<Location, Object> machines = new HashMap<>();

    public static <T> void createMachine(T type, Location loc) throws IOException {

        machines.put(loc, type);
    }

    public static void removeMachine(Location loc){
        machines.remove(loc);
    }

    public static Object getMachine(Location loc){
        return machines.get(loc);
    }

    /*
    public static Object updateMachine(Coords coords, Object newMachine){

        if (machines.containsKey(coords)) {
            machines.put(coords, newMachine);
            return newMachine;
        } else {
            return null;
        }
    }
    */

    public static void deleteMachineFile(Location loc){
        File file = new File(Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" +
                loc.getWorld().getName() + "/" + machines.get(loc).getClass().getSimpleName() + "/" + LocHelper.getXYZ(loc) + ".json");
        file.delete();
    }

    public static void saveAllMachines() throws IOException {

        for (Location loc : machines.keySet()) {
            Gson gson = new Gson();
            File file = new File(Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" +
                    loc.getWorld().getName() + "/" + machines.get(loc).getClass().getSimpleName() + "/" + LocHelper.getXYZ(loc) + ".json");
            file.getParentFile().mkdirs();
            file.createNewFile();                                               //   File path will look like:
            Writer writer = new FileWriter(file, false);                //   Mekanica/World/Coal/0,0,0.json
            gson.toJson(machines.get(loc), writer);
            writer.flush();
            writer.close();
        }
    }

    public static void saveChunkMachines(String worldName, int chunkX, int chunkZ) throws IOException {

        for (Location loc : machines.keySet()){
            if (loc.getWorld().getName().equals(worldName)){
                if (loc.getX()/16 == chunkX){
                    if (loc.getZ()/16 == chunkZ){
                        saveMachine(loc);
                    }
                }
            }
        }
    }

    public static void saveMachine(Location loc) throws IOException {

        Gson gson = new Gson();
        File file = new File(Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" +
                loc.getWorld().getName() + "/" + machines.get(loc).getClass().getSimpleName() + "/" + LocHelper.getXYZ(loc) + ".json");
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
        }
        Writer writer = new FileWriter(file, false);
        gson.toJson(machines.get(loc), writer);
        writer.flush();
        writer.close();
    }

    public static void loadAllMachines() throws ClassNotFoundException, IOException {

        String basepath = Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/";
        Filewalker filewalker = new Filewalker();
        filewalker.walk(basepath);

        Gson gson = new Gson();
        for (Path path : Filewalker.getPathSet()){

            Class c = Class.forName(ClassHelper.getFullClassName(path.getParent().getFileName().toString()));
            Reader reader = new FileReader(path.toFile());

            Object aClass = gson.fromJson(reader, c);

            World world = Bukkit.getServer().getWorld(path.getParent().getParent().getFileName().toString());
            String[] coords = path.getFileName().toString().replaceFirst("[.]json","").split(",");
            Location loc = new Location(world, Double.parseDouble(coords[0]),Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
            machines.put(loc, aClass);
        }
    }

    public static void loadChunkMachines(String worldName, int chunkX, int chunkZ) throws ClassNotFoundException, FileNotFoundException {

        String basepath = Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" + worldName + "/";
        Filewalker filewalker = new Filewalker();
        filewalker.walk(basepath);

        Gson gson = new Gson();
        for (Path path : Filewalker.getPathSet()){
            String[] XYZ = path.getFileName().toString().replaceFirst("[.]json","").split(",");

            int x = Integer.parseInt(XYZ[0]);
            int z = Integer.parseInt(XYZ[2]);

            if (x/16 == chunkX){
                if (z/16 == chunkZ){
                    Class c = Class.forName(ClassHelper.getFullClassName(path.getParent().getFileName().toString()));

                    Reader reader = new FileReader(path.toFile());

                    Object aClass = gson.fromJson(reader, c);

                    Location loc = new Location(Bukkit.getServer().getWorld(worldName), x, Integer.parseInt(XYZ[1]), z);
                    machines.put(loc, aClass);
                }
            }
        }
    }

    public static void loadMachine(Location loc) throws ClassNotFoundException, FileNotFoundException {

        String basepath = Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" + loc.getWorld().getName() + "/";
        Filewalker filewalker = new Filewalker();
        filewalker.walk(basepath);

        Gson gson = new Gson();
        for (Path path : Filewalker.getPathSet()){
            if (path.getFileName().toString().contains(LocHelper.getXYZ(loc))){
                Class c = Class.forName(ClassHelper.getFullClassName(path.getParent().getFileName().toString()));

                Reader reader = new FileReader(path.toFile());

                Object aClass = gson.fromJson(reader, c);

                machines.put(loc, aClass);
                break;
            }
        }
    }

}