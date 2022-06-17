package justadeni.mekanica.machines.generators;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Wind extends Machine {

    @Override
    public final ItemManager getItem() {
        return new ItemManager(6,"Wind Generator", Material.DISPENSER);
    }
    @Override
    public InvManager getInv(Location loc){

        if (InvIndex.get(loc) != null){
            return InvIndex.get(loc);
        }

        return new InvManager(new int[]{},new ItemStack[]{},new int[]{},new ItemStack[]{},getRF(), getLimit(),getProcon(), "Wind Generator");
    }
    public static Wind getNew(){
        return new Wind(0, 20000, (short) 0);
    }


    public Wind(int RF, int limit, short production) {
        super(RF, limit, production);
    }

    @Override
    public void produce(Location loc) {

        InvManager invManager = InvIndex.get(loc);
        if (invManager != null){
            invManager.getChange();
        }

        if (getRF() >= getLimit()) {
            setProcon((short) 0);
            return;
        }
        if (loc.getY() > 50) {
            int delta = (int) (Math.pow(loc.getY(), 0.6) - 10); //see https://www.desmos.com/calculator/uwv6sxdubl
            Biome biome = loc.getWorld().getBiome(loc);
            if (biome.equals(Biome.MEADOW) || biome.equals(Biome.JAGGED_PEAKS) || biome.equals(Biome.FROZEN_PEAKS)
                    || biome.equals(Biome.STONY_PEAKS) || biome.equals(Biome.GROVE) || biome.equals(Biome.SNOWY_SLOPES)) {
                delta *= 1.25;
            }

            addRF(delta);
            setProcon((short) delta);
        }

        if (invManager != null){
            invManager.makeChange(new ItemStack[]{}, new ItemStack[]{}, getRF(), getLimit());
        }
    }
}
