package justadeni.mekanica.utils.nms;

import justadeni.mekanica.utils.files.Storage;
import org.bukkit.block.Block;

import java.lang.reflect.Field;

public class Breakability {

    public static boolean isBreakable(Block block){
        if (Storage.getMachine(block.getLocation()) == null){
            return false;
        }

        try {
            Object b = Class.forName("util.CraftMagicNumbers").getMethod("getBlock").invoke(block);
            Field field = b.getClass().getDeclaredField("strength");
            field.setAccessible(true);
            float strenght = field.getFloat(b);
            if (strenght <= 55){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
