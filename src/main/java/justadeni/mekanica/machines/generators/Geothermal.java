package justadeni.mekanica.machines.generators;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.LocHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Geothermal extends Machine {

    @Override
    public final ItemManager getItem() {
        return new ItemManager(4,"Geothermal Generator", Material.OBSERVER);
    }
    @Override
    public InvManager getInv(Location loc){

        if (InvIndex.get(loc) != null){
            return InvIndex.get(loc);
        }

        return new InvManager(new int[]{},new ItemStack[]{},new int[]{},new ItemStack[]{},getRF(), getLimit(),getProcon(), "Geothermal Generator");
    }

    public static Geothermal getNew(){
        return new Geothermal(0, 20000, (short) 0);
    }

    public Geothermal(int RF, int limit, short production) {
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
        int delta = 0;
        for (Location around : LocHelper.getLocationsAround(loc)){
            Material material = around.getBlock().getType();
            if (!material.isAir()){
                if (material.equals(Material.COAL_BLOCK) || material.equals(Material.LAVA) || material.equals(Material.MAGMA_BLOCK)){
                    delta += 1;
                }
            }
        }

        addRF(delta);
        setProcon((short) delta);

        if (invManager != null){
            invManager.makeChange(new ItemStack[]{}, new ItemStack[]{}, getRF(), getLimit());
        }
    }
}
