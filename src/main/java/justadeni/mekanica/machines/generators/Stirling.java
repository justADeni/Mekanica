package justadeni.mekanica.machines.generators;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.nms.BurnUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Stirling extends Machine {

    public final static ItemManager itemManager = new ItemManager(2,"Stirling Generator", Material.BLAST_FURNACE);
    public static Stirling getNew(){
        return new Stirling(0, 80000, (short) 0, new ItemStack(Material.AIR), (byte) 0);
    }
    private ItemStack fuel;
    private byte progress;

    public Stirling( int RF, int limit, short production, ItemStack fuel, byte progress) {
        super(RF, limit, production);
        this.fuel = fuel;
        this.progress = progress;
    }

    @Override
    public void produce(Location loc){
        if (getRF() >= getLimit()) {
            setProcon((short) 0);
            return;
        }
        if (progress == 0){
            if (fuel.getType().isFuel()){
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
        int delta = BurnUtils.getBurnTime(fuel.getType())/10;
        addRF(delta);
        setProcon((short) delta);
    }
}
