package justadeni.mekanica.listeners;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.GameMode;
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

            //int id = ClassHelper.getIdByObject((Machine) Storage.getMachine(loc));
            int id = (Storage.getMachine(loc)).getItem().getId();
            if (id > 0){
                e.setDropItems(false);
                if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ItemManager.getItem(id));
                }
            }

            InvIndex.remove(loc);
            Storage.deleteMachineFile(loc);
            Storage.removeMachine(loc);
        }
    }

}
