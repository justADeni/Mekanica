package justadeni.mekanica.listeners;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockClick implements Listener {

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e){
        //Mekanica.log(e.toString());

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){

            Location loc = e.getClickedBlock().getLocation();
            Player p = e.getPlayer();

            if (Storage.getMachine(loc) != null) {
                if (clickAble(e.getPlayer())) {
                    Machine machine = Storage.getMachine(loc);

                    if (InvIndex.get(loc) != null){
                        p.openInventory(InvIndex.get(loc).getInventory());
                    } else {
                        InvManager invManager = machine.getInv(loc);
                        p.openInventory(invManager.getInventory());
                        InvIndex.put(loc, invManager);
                    }
                    e.setCancelled(true);
                }
            }

        }
    }

    private boolean clickAble(Player p){
        if (p.getInventory().getItemInMainHand().getType().isAir() && p.getInventory().getItemInOffHand().getType().isAir()){
            return true;
        }
        if (p.getInventory().getItemInMainHand().getType().isBlock() || p.getInventory().getItemInMainHand().getType().isBlock()){
            return false;
        }
        return true;
    }
}
