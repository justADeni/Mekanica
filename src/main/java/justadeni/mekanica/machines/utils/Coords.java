package justadeni.mekanica.machines.utils;


import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;


public class Coords {

    private int x;
    private int y;
    private int z;
    private String world;

    public Coords(String world, int x, int y, int z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorldName(){
        return world;
    }
    public String getXYZ(){
        return x + "," + y + "," + z;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getZ(){
        return z;
    }

    public static String toString(Coords coords){
        return coords.world + "," + coords.x + "," + coords.y + "," + coords.z;
    }

    public static Coords fromString(String string){
        String[] strings = string.split(",");
        return new Coords(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]),Integer.parseInt(strings[3]));
    }


    public World getWorld(){
        return Bukkit.getWorld(world);
    }

    public Chunk getChunk(Coords coords){
        return getWorld().getChunkAt(coords.x/16, coords.z/16);
    }
    public Coords getChunkCoords(Coords coords){
        return new Coords(coords.world, coords.x/16,0,coords.z/16);
    }

    public Location getLocation(Coords coords){
        return new Location(getWorld(), coords.x, coords.y, coords.z);
    }
    public Coords toLocation(Location loc){
        return new Coords(loc.getWorld().getName(),(int) loc.getX(),(int) loc.getY(),(int) loc.getZ());
    }
}
