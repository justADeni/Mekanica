package justadeni.mekanica.utils;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@Setter
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

    public World getBukkitWorld(){
        return Bukkit.getWorld(world);
    }

    public static String toString(Coords coords){
        return coords.world + "," + coords.x + "," + coords.y + "," + coords.z;
    }

    public static Coords fromString(String string) {
        String[] strings = string.split(",");
        return new Coords(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
    }

    public static Chunk getChunk(Coords coords){
        return coords.getBukkitWorld().getChunkAt(coords.x/16, coords.z/16);
    }
    public static Coords getChunkCoords(Coords coords){
        return new Coords(coords.getWorld(), coords.x/16,0,coords.z/16);
    }

    public static Location getLocation(Coords coords){
        return new Location(coords.getBukkitWorld(), coords.x, coords.y, coords.z);
    }
    public static Coords fromLocation(Location loc){
        return new Coords(loc.getWorld().getName(),(int) loc.getX(),(int) loc.getY(),(int) loc.getZ());
    }
}
