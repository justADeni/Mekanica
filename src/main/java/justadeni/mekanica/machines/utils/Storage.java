package justadeni.mekanica.machines.utils;

import com.google.common.reflect.ClassPath;
import com.google.gson.Gson;
import justadeni.mekanica.Mekanica;
import justadeni.mekanica.machines.generators.Coal;

import java.io.*;
import java.nio.file.Path;

import java.util.*;


public class Storage {

    private static HashMap<Coords, Object> machines = new HashMap<>();

    //TODO: redo this to be compatible with all types
    public static <T> HashMap createMachine(T type, Coords coords) throws IOException {

        if (type instanceof Coal){
            //Coal coal = new Coal((Integer) arrayList.get(0),(Integer) arrayList.get(1),(ArrayList)arrayList.get(2), (Short) arrayList.get(3), (Integer) arrayList.get(4), (Byte) arrayList.get(5));

            Coal coal = (Coal) type;
            machines.put(coords, coal);
            saveAllMachines();

            return machines;
        }
        return null;
    }

    public static void removeMachine(Coords coords){
        machines.remove(coords);
    }

    public static Object getMachine(Coords coords){
        return machines.get(coords);
    }

    public static Object updateMachine(Coords coords, Object newMachine){

        if (machines.containsKey(coords)) {
            machines.put(coords, newMachine);
            return newMachine;
        } else {
            return null;
        }
    }



    public static void saveAllMachines() throws IOException {

        for (Coords coords : machines.keySet()) {
            Gson gson = new Gson();
            File file = new File(Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" +
                    coords.getWorldName() + "/" + machines.get(coords).getClass().getSimpleName() + "/" + coords.getXYZ() + ".json");
            file.getParentFile().mkdirs();
            file.createNewFile();                                               //   File path will look like:
            Writer writer = new FileWriter(file, false);                //   Mekanica/World/Coal/0,0,0.json
            gson.toJson(machines.get(coords), writer);
            writer.flush();
            writer.close();
        }
    }

    public static void saveChunkMachines(String worldName, int chunkX, int chunkZ) throws IOException {

        for (Coords coords : machines.keySet()){
            if (coords.getWorldName().equals(worldName)){
                if (coords.getX()/16 == chunkX){
                    if (coords.getZ()/16 == chunkZ){
                        saveMachine(coords);
                    }
                }
            }
        }
    }

    public static void saveMachine(Coords coords) throws IOException {

        Gson gson = new Gson();
        File file = new File(Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" +
                coords.getWorldName() + "/" + machines.get(coords).getClass().getSimpleName() + "/" + coords.getXYZ() + ".json");
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
        }
        Writer writer = new FileWriter(file, false);
        gson.toJson(machines.get(coords), writer);
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

            Coords coords = Coords.fromString(path.getParent().getParent().getFileName().toString() + "," + path.getFileName().toString().replaceFirst("[.]json", ""));
            machines.put(coords, aClass);
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

                    machines.put(new Coords(worldName, x, Integer.parseInt(XYZ[1]), z), aClass);
                }
            }
        }
    }

    public static void loadMachine(Coords coords) throws ClassNotFoundException, FileNotFoundException {
        String basepath = Mekanica.getPlugin().getDataFolder().getAbsolutePath() + "/Data/" + coords.getWorldName() + "/";
        Filewalker filewalker = new Filewalker();
        filewalker.walk(basepath);

        Gson gson = new Gson();
        for (Path path : Filewalker.getPathSet()){
            if (path.getFileName().toString().contains(Coords.toString(coords))){
                Class c = Class.forName(ClassHelper.getFullClassName(path.getParent().getFileName().toString()));

                Reader reader = new FileReader(path.toFile());

                Object aClass = gson.fromJson(reader, c);

                machines.put(coords, aClass);
                break;
            }
        }
    }

}
