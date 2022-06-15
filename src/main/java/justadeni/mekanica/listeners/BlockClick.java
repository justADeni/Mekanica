package justadeni.mekanica.listeners;

import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockClick {

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){

            Machine machine = (Machine) Storage.getMachine(e.getClickedBlock().getLocation());

            if (machine != null){
                e.getPlayer().openInventory(machine.getInv().getInventory());
            }
        }
    }

}
