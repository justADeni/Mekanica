package justadeni.mekanica.inventories;

import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.ClassHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class InvManager {
    //private final static byte[] positions = {10,14,37,41};
    private int[] inSlots;
    private ItemStack[] inItems;
    private int[] outSlots;
    private ItemStack[] outItems;
    private Inventory inventory;
    public InvManager(int[] inSlots, ItemStack[] inItems, int[] outSlots,ItemStack[] outItems, Machine machine){
        this.inSlots = inSlots;
        this.inItems = inItems;
        this.outSlots = outSlots;
        this.outItems = outItems;

        inventory = Bukkit.createInventory(null, getTotalSlots(), ClassHelper.getName(machine)); //27, 54
        if (inSlots.length != 0){
            for (int i : inSlots){
                setCircle(i,Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                inventory.setItem(i,inItems[i]);
            }
        }
        if (outSlots.length != 0){
            for (int i : outSlots){
                setCircle(i,Material.RED_STAINED_GLASS_PANE);
                inventory.setItem(i,outItems[i]);
            }
        }
        setEnergyBar(machine.getRF(), machine.getLimit());
        fillRest(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
    }
    private int getTotalSlots(){
        int slots = 27;
        if ((inSlots.length+outSlots.length) > 2){
            slots = 54;
        }
        return slots;
    }
    private void setCircle(int center, Material material){

        ItemStack item = new ItemStack(material);

        inventory.setItem(center-9-1, item);
        inventory.setItem(center-9, item);
        inventory.setItem(center-9+1, item);

        inventory.setItem(center-1, item);
        inventory.setItem(center+1, item);

        inventory.setItem(center+9-1, item);
        inventory.setItem(center+9, item);
        inventory.setItem(center+9+1, item);
    }

    private void setEnergyBar(int RF, int limit){
        int index = (inventory.getSize()/9);
        int greenCount = (int) Math.floor(((double) RF/limit)*index);
        while (index > 0){
            if (greenCount > 0) {
                inventory.setItem((index*9)-1, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
            } else {
                inventory.setItem((index*9)-1, new ItemStack(Material.RED_STAINED_GLASS_PANE));
            }
            index --;
            greenCount--;
            /*
            if (index <= 0){
                break;
            }
            */
        }
    }

    private void fillRest(Material material){
        ItemStack item = new ItemStack(material);
        for (int i = inventory.getSize()-1; i >= 0; i--){
            if (inventory.getItem(i) != null || inventory.getItem(i).equals(new ItemStack(Material.AIR))){
                inventory.setItem(i, item);
            }
        }
    }
}
