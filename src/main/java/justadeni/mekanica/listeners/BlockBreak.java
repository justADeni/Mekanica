package justadeni.mekanica.listeners;

import justadeni.mekanica.Mekanica;
import justadeni.mekanica.utils.Coords;
import justadeni.mekanica.utils.Storage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Coords coords = Coords.fromLocation(e.getBlock().getLocation());

        Mekanica.log("Block Break");
        Mekanica.log("Location " + e.getBlock().getLocation());
        Mekanica.log("Coords " + Coords.toString(coords));
        Mekanica.log("HashMap " + Storage.getMachine(coords));
        Mekanica.log("");

        if (!(Storage.getMachine(coords) == null)) {

            Storage.deleteMachineFile(coords);
            Storage.removeMachine(coords);
        }
    }

}
