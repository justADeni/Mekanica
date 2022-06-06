package justadeni.mekanica.listeners;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.Storage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) throws Exception {

        Location loc = e.getBlock().getLocation();
        Object obj = Storage.getMachine(loc);

        if (!(obj == null)) {

            int id = ClassHelper.getIdByObject(Storage.getMachine(loc));
            if (id > 0){
                //e.getBlock().getDrops().add(ItemManager.getItem(id));
                e.setDropItems(false);
                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ItemManager.getItem(id));
            }

            Storage.deleteMachineFile(loc);
            Storage.removeMachine(loc);
        }
    }

}
