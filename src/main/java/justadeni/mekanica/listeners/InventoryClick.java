package justadeni.mekanica.listeners;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.Iterator;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getClickedInventory() == null){
            return;
        }
        if (!(e.getClickedInventory().getHolder() instanceof InvManager)){
            return;
        }

        InvManager invManager = InvIndex.getManagerByInv(e.getClickedInventory());

        ArrayList<Integer> localInSlots = new ArrayList<>();
        if (invManager.getInSlots().length != 0){
            for (int i : invManager.getInSlots()){
                localInSlots.add(i);
            }
        }

        ArrayList<Integer> localOutSlots = new ArrayList<>();
        if (invManager.getOutSlots().length != 0){
            for (int i : invManager.getOutSlots()){
                localOutSlots.add(i);
            }
        }

        int rawSlot = e.getSlot();
        InventoryAction action = e.getAction();

        if (localOutSlots.contains(rawSlot)){
            if (action.toString().contains("PUT") || action.toString().contains("SWAP")){
                e.setCancelled(true);
            }
        } else if (localInSlots.contains(rawSlot)){

        } else {
            e.setCancelled(true);
        }
    }

}
