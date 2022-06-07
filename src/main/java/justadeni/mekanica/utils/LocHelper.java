package justadeni.mekanica.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class LocHelper {

    public static String getXYZ(Location loc){
        return ((int) loc.getX()) + "," + ((int) loc.getY()) + "," + ((int) loc.getZ());
    }

    public static Location[] getLocationsAround(Location loc){
        World world = loc.getWorld();

        int x = (int) loc.getX();
        int y = (int) loc.getY();
        int z = (int) loc.getZ();

        Location[] locs = new Location[6];
        locs[0] = new Location(world, x+1,y,z);
        locs[1] = new Location(world, x-1,y,z);
        locs[2] = new Location(world, x,y+1,z);
        locs[3] = new Location(world, x,y-1,z);
        locs[4] = new Location(world, x,y,z+1);
        locs[5] = new Location(world, x,y,z-1);

        return locs;
    }
}
