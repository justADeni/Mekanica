package justadeni.mekanica.listeners;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Iterator;

public class InventoryClose implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Iterator it = InvIndex.getMap().keySet().iterator();
        while (it.hasNext()) {
            Location loc = (Location) it.next();
            if (InvIndex.get(loc).getInventory().equals(e.getInventory())){
                it.remove();
            }
        }
    }

}
