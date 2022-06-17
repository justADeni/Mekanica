package justadeni.mekanica.machines.generators;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Entropy extends Machine {

    @Override
    public final ItemManager getItem() {
        return new ItemManager(5,"Entropy Generator", Material.ANVIL);
    }
    @Override
    public InvManager getInv(Location loc){

        if (InvIndex.get(loc) != null){
            return InvIndex.get(loc);
        }

        return new InvManager(new int[]{10},new ItemStack[]{fuel},new int[]{},new ItemStack[]{},getRF(), getLimit(), "Entropy Generator");
    }

    public static Entropy getNew(){
        return new Entropy(0, 10000, (short) 0, new ItemStack(Material.AIR), (byte) 0);
    }

    private ItemStack fuel;

    private byte progress;

    public Entropy(int RF, int limit, short production, ItemStack fuel, byte progress) {
        super(RF, limit, production);
        this.fuel = fuel;
        this.progress = progress;
    }

    @Override
    public void produce(Location loc) {

        if (getRF() >= getLimit()) {
            setProcon((short) 0);
            return;
        }
        if (progress == 0){
            if (!fuel.getType().isAir()){
                if (fuel.getAmount() == 1){

                    work();
                    setFuel(new ItemStack(Material.AIR));
                } else {

                    work();
                    setFuel(new ItemStack(fuel.getType(), fuel.getAmount()-1));
                }
            } else {
                setProcon((short) 0);
                return;
            }
        } else if (progress <= 90){

            work();

        } else {

            work();
            progress = 0;

        }

    }

    private void work(){
        progress += 10;
        int delta = 2;
        addRF(delta);
        setProcon((short) delta);
    }
}
