package justadeni.mekanica.utils;

import org.bukkit.Location;

public class LocHelper {

    public static String getXYZ(Location loc){
        return ((int) loc.getX()) + "," + ((int) loc.getY()) + "," + ((int) loc.getZ());
    }

}
