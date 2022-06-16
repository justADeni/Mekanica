package justadeni.mekanica.listeners;

import justadeni.mekanica.Mekanica;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
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
            if (Storage.getMachine(e.getClickedBlock().getLocation()) != null) {
                if (clickAble(e.getPlayer())) {
                    Machine machine = Storage.getMachine(e.getClickedBlock().getLocation());

                    e.setCancelled(true);
                    e.getPlayer().openInventory(machine.getInv().getInventory());
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
