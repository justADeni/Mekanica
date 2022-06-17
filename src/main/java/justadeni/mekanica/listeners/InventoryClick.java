package justadeni.mekanica.listeners;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getClickedInventory() == null){
            return;
        }
        if (!(e.getClickedInventory() instanceof InvManager)){
            return;
        }

        InvManager invManager = InvIndex.getManagerByInv(e.getClickedInventory());

        ArrayList<Integer> localInSlots = new ArrayList<>();
        if (invManager.getInSlots().length != 0){
            for (int i : invManager.getInSlots()){
                localInSlots.add(invManager.getInSlots()[i]);
            }
        }

        ArrayList<Integer> localOutSlots = new ArrayList<>();
        if (invManager.getInSlots().length != 0){
            for (int i : invManager.getOutSlots()){
                localOutSlots.add(invManager.getOutSlots()[i]);
            }
        }

        int rawSlot = e.getRawSlot();
        if (localOutSlots.contains(rawSlot)){
            if (e.getAction().toString().contains("PUT") || e.getAction().toString().contains("SWAP")){
                e.setCancelled(true);
            }
        }
    }

}
