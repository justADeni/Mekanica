package justadeni.mekanica.listeners;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.Coords;
import justadeni.mekanica.utils.Storage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, IOException {

            ItemStack item = e.getItemInHand();
            int id = ItemManager.getId(item);
            if (id > 0){
                Coords coords = Coords.fromLocation(e.getBlockPlaced().getLocation());

                Storage.createMachine(ClassHelper.getNewClassObject(id), coords);
                Storage.saveMachine(coords);
            }
    }

}
