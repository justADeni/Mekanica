package justadeni.mekanica.listeners;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.Storage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;


public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) throws Exception {

            ItemStack item = e.getItemInHand();
            int id = ItemManager.getId(item);
            if (id > 0){
                Object obj  = ClassHelper.getNewClassObject(id);
                if (!(obj == null)) {

                    Location loc = e.getBlockPlaced().getLocation();
                    Storage.createMachine(obj, loc);
                    Storage.saveMachine(loc);
                }
            }
    }

}
