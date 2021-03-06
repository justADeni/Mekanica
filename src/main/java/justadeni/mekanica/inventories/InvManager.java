package justadeni.mekanica.inventories;

import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.ClassHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class InvManager implements InventoryHolder {
    //private final static byte[] positions = {10,14,37,41};
    private short procon;
    private int[] inSlots;
    private ItemStack[] inItems;
    private int[] outSlots;
    private ItemStack[] outItems;
    private Inventory inventory;
    public InvManager(int[] inSlots, ItemStack[] inItems, int[] outSlots,ItemStack[] outItems, int RF, int limit, short procon, String name){
        this.inSlots = inSlots;
        this.inItems = inItems;
        this.outSlots = outSlots;
        this.outItems = outItems;
        this.procon = procon;

        inventory = Bukkit.createInventory(this, getTotalSlots(), name); //27, 54
        if (inSlots.length != 0) {
            int count = 0;
            for (int i : inSlots) {
                setCircle("In/Out Slot", i, Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                inventory.setItem(i, inItems[count]);
                count++;
            }
        }
        if (outSlots.length != 0) {
            int count = 0;
            for (int i : outSlots) {
                setCircle("Out Slot", i, Material.RED_STAINED_GLASS_PANE);
                inventory.setItem(i, outItems[count]);
                count++;
            }
        }
        setEnergyBar(RF, limit);
        fillRest(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
    }

    public void getChange(){
        if (getInSlots().length != 0){
            int count = 0;
            for (int i : getInSlots()){
                inItems[count] = getInventory().getItem(i);
                count++;
            }
        }
        if (getOutSlots().length != 0){
            int count = 0;
            for (int i : getOutSlots()){
                outItems[count] = getInventory().getItem(i);
                count++;
            }
        }
    }
    public void makeChange(ItemStack[] inItems,ItemStack[] outItems, int RF, int limit){
        setInItems(inItems);
        setOutItems(outItems);

        if (getInSlots().length != 0){
            int count = 0;
            for (int i : getInSlots()){
                setCircle("In/Out Slot", i, Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                getInventory().setItem(i,inItems[count]);
                count++;
            }
        }
        if (getOutSlots().length != 0){
            int count = 0;
            for (int i : getOutSlots()){
                setCircle("Out Slot", i, Material.RED_STAINED_GLASS_PANE);
                getInventory().setItem(i,outItems[count]);
                count++;
            }
        }
        setEnergyBar(RF, limit);
    }
    private int getTotalSlots(){
        int slots = 27;
        if ((inSlots.length+outSlots.length) > 2){
            slots = 54;
        }
        return slots;
    }
    private void setCircle(String name, int center, Material material){

        ItemStack item = itemMaker(name, material, false);
        if (procon > 0){
            item = itemMaker(name, material, true, "Progress: " + procon);
        }

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
        int index = (inventory.getSize()/9);                //lets say 3
        double greenCount = ((double) RF/limit)*index;      //lets say (1/2)*3 then (0.5)*2 etc

        while (index > 0){                                  //of course true at first
            if (greenCount > 0 ) {
                inventory.setItem((index*9)-1, itemMaker("RF: " + RF + "/" + limit, Material.GREEN_STAINED_GLASS_PANE, false));
            } else if (Math.round(greenCount) == greenCount) {
                inventory.setItem((index*9)-1, itemMaker("RF: " + RF + "/" + limit, Material.YELLOW_STAINED_GLASS_PANE, false));
            } else {
                inventory.setItem((index*9)-1, itemMaker("RF: " + RF + "/" + limit, Material.RED_STAINED_GLASS_PANE, false));
            }
            index --;
            greenCount--;
        }
    }

    private void fillRest(Material material){
        for (int i = 0; i < inventory.getSize(); i++){

            List<Integer> slots = new ArrayList<>();
            for (int slot : inSlots)
                slots.add(slot);
            for (int slot : outSlots)
                slots.add(slot);

            if (slots.contains(i))
                continue;

            if (inventory.getItem(i) == null){
                inventory.setItem(i, itemMaker(" ", material, false));
            }
        }
    }

    private static ItemStack itemMaker(String DisplayName, Material material, boolean glow){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DisplayName);
        if (glow) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack itemMaker(String DisplayName, Material material, boolean glow, String lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DisplayName);
        List<String> list = meta.getLore();
        list.add(lore);
        meta.setLore(list);
        if (glow) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }
}
