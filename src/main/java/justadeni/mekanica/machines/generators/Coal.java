package justadeni.mekanica.machines.generators;

import justadeni.mekanica.inventories.InvIndex;
import justadeni.mekanica.inventories.InvManager;
import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.files.Storage;
import justadeni.mekanica.utils.nms.BurnUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Coal extends Machine{

    @Override
    public final ItemManager getItem() {
        return new ItemManager(1,"Coal Generator", Material.DROPPER);
    }

    @Override
    public InvManager getInv(Location loc){
        return new InvManager(new int[]{10},new ItemStack[]{fuel},new int[]{},new ItemStack[]{},getRF(), getLimit(),getProcon(), "Coal Generator");
    }
    public static Coal getNew(){
        return new Coal(0, 80000, (short) 0, new ItemStack(Material.AIR), (byte) 0);
    }

    private ItemStack fuel;
    private int progress;

    public Coal( int RF, int limit, short procon, ItemStack fuel, byte progress) {
        super(RF, limit, procon);
        this.fuel = fuel;
        this.progress = progress;
    }

    @Override
    public void produce(Location loc) {

        InvManager invManager = InvIndex.get(loc);
        if (invManager != null){
            invManager.getChange();
            fuel = invManager.getInItems()[0];
        }

        if (getRF() >= getLimit()) {
            setProcon((short) 0);
            return;
        }
        if (progress == 0){
            if (fuel == null){
                setProcon((short) 0);
                return;
            } else if (fuel.getType().equals(Material.COAL) || fuel.getType().equals(Material.CHARCOAL)){
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

        if (invManager != null){
            invManager.makeChange(new ItemStack[]{fuel}, new ItemStack[]{}, getRF(), getLimit());
        }
    }

    private void work(){
        progress += 10;
        int delta = BurnUtils.getBurnTime(Material.COAL)/10;
        addRF(delta);
        setProcon((short) delta);
    }
}



