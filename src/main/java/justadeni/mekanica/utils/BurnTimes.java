package justadeni.mekanica.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftFurnace;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

public class BurnTimes {

    public static int getBurnTime(Material material) {

        Item item = CraftItemStack.asNMSCopy(new ItemStack(material, 1)).getItem();
        if (FurnaceBlockEntity.getFuel().containsKey(item)) {
            return FurnaceBlockEntity.getFuel().get(item);
        }
        return 0;
    }

}
