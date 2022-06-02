package justadeni.mekanica.items;

import justadeni.mekanica.utils.ClassHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemManager {

    private int id;
    private String DisplayName;
    private Material material;

    public ItemManager(int id, String Displayname, Material material) {
        this.id = id;
        this.DisplayName = Displayname;
        this.material = material;
    }

    public static ItemStack getItem(int id) throws Exception {
        ItemManager itemManager = ClassHelper.getItemManager(id);
        return ItemMaker(id, itemManager.getDisplayName(), itemManager.getMaterial());
    }

    public static int getId(ItemStack itemStack){
        try {
            return Integer.parseInt(itemStack.getItemMeta().getLore().get(0).split(" ")[1]);
        } catch (Error e){
            return 0;
        }
    }

    private static ItemStack ItemMaker(int id, String DisplayName, Material material){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(DisplayName);
        List<String> lore = new ArrayList<>();
        lore.add("ID: " + id);
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

}
