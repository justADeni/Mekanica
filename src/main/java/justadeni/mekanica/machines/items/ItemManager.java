package justadeni.mekanica.machines.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemManager {

    public static ItemStack getItem(int id){
        switch (id){
            case 1 -> {
                return ItemMaker("Coal Generator", Material.DROPPER, id);
            }
            case 2 -> {
                return ItemMaker("Stirling Generator", Material.BLAST_FURNACE, id);
            }
            case 3 -> {
                return ItemMaker("Solar Generator", Material.DAYLIGHT_DETECTOR, id);
            }
        }
        return null;
    }

    private static ItemStack ItemMaker(String DisplayName, Material material, int id){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DisplayName);
        List<String> lore = meta.getLore();
        lore.add("ID: " + id);
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

}
