package justadeni.mekanica.listeners;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) throws Exception {

        Location loc = e.getBlock().getLocation();
        Machine machine = Storage.getMachine(loc);

        if (machine != null) {

            //int id = ClassHelper.getIdByObject((Machine) Storage.getMachine(loc));
            int id = machine.getItem().getId();
            if (id > 0){
                e.setDropItems(false);
                if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                    loc.getWorld().dropItem(loc, ItemManager.getItem(id));

                    for (ItemStack itemStack : machine.getInv(loc).getInItems()){
                        loc.getWorld().dropItem(loc, itemStack);
                    }
                    for (ItemStack itemStack : machine.getInv(loc).getOutItems()){
                        loc.getWorld().dropItem(loc, itemStack);
                    }
                }
            }

            if (InvIndex.get(loc) != null) {
                Inventory inv = InvIndex.get(loc).getInventory();
                if (inv.getViewers().size() > 0){
                    for (HumanEntity p : inv.getViewers()){
                        p.closeInventory();
                    }
                }
                InvIndex.remove(loc);
            }
            Storage.deleteMachineFile(loc);
            Storage.removeMachine(loc);
        }
    }

}
