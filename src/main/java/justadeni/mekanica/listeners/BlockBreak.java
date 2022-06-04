package justadeni.mekanica.listeners;

import justadeni.mekanica.Mekanica;
import justadeni.mekanica.utils.Storage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Location loc = e.getBlock().getLocation();

        if (!(Storage.getMachine(loc) == null)) {

            Storage.deleteMachineFile(loc);
            Storage.removeMachine(loc);
        }
    }

}
