package justadeni.mekanica.utils.nms;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

public class BurnUtils {

    public static int getBurnTime(Material material) {

        Item item = CraftItemStack.asNMSCopy(new ItemStack(material, 1)).getItem();
        if (FurnaceBlockEntity.getFuel().containsKey(item)) {
            return FurnaceBlockEntity.getFuel().get(item);
        }
        return 0;
    }

    //TODO: make these indexed in a file
    public static boolean isBurnable(Material material){

        if (!material.isAir()) {

            Iterator<Recipe> recipes = Bukkit.recipeIterator();
            while (recipes.hasNext()) {
                Recipe recipe = recipes.next();
                if (!(recipe instanceof FurnaceRecipe frecipe)) continue;
                if (frecipe.getInput().getType().equals(material)) {
                    return true;
                }
            }
        }
        return false;
    }

    //TODO: make these indexed in a file
    public static Material getResult(Material material){

        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = recipes.next();
            if (!(recipe instanceof FurnaceRecipe frecipe)) continue;
            if (frecipe.getInput().getType().equals(material)) {
                return frecipe.getResult().getType();
            }
        }
        return null;
    }

}
