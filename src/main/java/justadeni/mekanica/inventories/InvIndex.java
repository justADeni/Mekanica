package justadeni.mekanica.inventories;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InvIndex {

    private static HashMap<Location, InvManager> inventoryIndex = new HashMap<>();

    public static void put(Location loc, InvManager invManager){
        inventoryIndex.put(loc, invManager);
    }
    public static InvManager get(Location loc){
        return inventoryIndex.get(loc);
    }

    public static HashMap<Location, InvManager> getMap(){
        return inventoryIndex;
    }

    public static void remove(Location loc){
        inventoryIndex.remove(loc);
    }
    public static Location getLocByInv(Inventory inv) {
        for (Location loc : inventoryIndex.keySet()) {
            InvManager invManager = inventoryIndex.get(loc);
            if (inv.equals(invManager.getInventory())) {
                return loc;
            }
        }
        return null;
    }
    public static InvManager getManagerByInv(Inventory inv){
        for (Location loc : inventoryIndex.keySet()) {
            InvManager invManager = inventoryIndex.get(loc);
            if (inv.equals(invManager.getInventory())) {
                return invManager;
            }
        }
        return null;
    }

}
