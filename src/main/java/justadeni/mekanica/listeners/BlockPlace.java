package justadeni.mekanica.listeners;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;


public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){

            ItemStack item = e.getItemInHand();
            int id = ItemManager.getId(item);
            if (id > 0){
                //Object obj = ClassHelper.getNewItem(id);
                //Object obj = ItemManager.getItem(id);
                Machine machine = ClassHelper.getNewMachineById(id);

                Location loc = e.getBlockPlaced().getLocation();
                Storage.putMachine(machine, loc);
                Storage.saveMachine(loc);
            }
    }

}
