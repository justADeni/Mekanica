package justadeni.mekanica.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvHelper {

    public static int setCircle(Inventory inv, int leftUp, Material material){

        ItemStack item = new ItemStack(material);
        inv.setItem(leftUp, item);
        inv.setItem(leftUp+1, item);
        inv.setItem(leftUp+2, item);

        inv.setItem(leftUp+9, item);
        inv.setItem(leftUp+9+2, item);

        inv.setItem(leftUp+18, item);
        inv.setItem(leftUp+18+1, item);
        inv.setItem(leftUp+18+2, item);

        return leftUp+9+1;
    }

    public static void setEnergyBar(Inventory inv, int RF, int maxRF){
        byte trueSize = (byte) (inv.getSize()+1);
        byte index = (byte) (trueSize/9);
        byte greenCount = (byte) Math.floor(((double) RF/maxRF)*index);
        while (index > 0){
            if (greenCount > 0) {
                inv.setItem((index*9)-1, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
            } else {
                inv.setItem((index*9)-1, new ItemStack(Material.RED_STAINED_GLASS_PANE));
            }
            index --;
            greenCount--;
            if (index <= 0){
                break;
            }
        }
    }

    public static void fillRest(Inventory inv, Material material){
        ItemStack item = new ItemStack(material);
        for (int i = inv.getSize(); i >= 0; i--){
            if (inv.getItem(i) != null || inv.getItem(i).equals(new ItemStack(Material.AIR))){
                inv.setItem(i, item);
            }
        }
    }
}
