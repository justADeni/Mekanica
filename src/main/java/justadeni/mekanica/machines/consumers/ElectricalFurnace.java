package justadeni.mekanica.machines.consumers;

import justadeni.mekanica.inventories.InvManager;
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
public class ElectricalFurnace extends Machine {

    @Override
    public final ItemManager getItem() {
        return new ItemManager(7,"Electrical Furnace", Material.FURNACE);
    }
    @Override
    public InvManager getInv(){
        return new InvManager(new int[]{10},new ItemStack[]{burnable},new int[]{14},new ItemStack[]{burned}, this);
    }

    public static ElectricalFurnace getNew(){
        return new ElectricalFurnace(0, 40000, (short) 0, new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR),(byte) 0);
    }

    private ItemStack burnable;
    private ItemStack burning;
    private ItemStack burned;

    private byte progress;
    public ElectricalFurnace(int RF, int limit, short procon, ItemStack burnable, ItemStack burning , ItemStack burned, byte progress) {
        super(RF, limit, procon);
        this.burnable = burnable;
        this.burning = burning;
        this.burned = burned;
        this.progress = progress;
    }

    @Override
    public void produce(Location loc) {
        if (getRF() <= 0){
            setProcon((short) 0);
            return;
        }

        if (progress == 0){
            if (burning.getType().isAir()) {
                if (BurnUtils.isBurnable(burnable.getType())) {

                    if (burnable.getAmount() == 1) {
                        work();
                        burning = burnable;
                        setBurnable(new ItemStack(Material.AIR));
                    } else {
                        work();
                        burning = new ItemStack(burnable.getType(), 1);
                        setBurnable(new ItemStack(burnable.getType(), burnable.getAmount() - 1));
                    }
                } else {
                    setProcon((short) 0);
                }
            } else if (burned.getType().isAir() || BurnUtils.getResult(burning.getType()).equals(burned.getType())){
                addBurned();
                setProcon((short) 0);
            }

        } else if (progress <= 80){
            work();
        } else {
            addBurned();
            work();
            progress = 0;
        }


    }

    private void work(){
        if (progress < 100) {
            progress += 20;
        }
        int delta = 50;
        subtractRF(delta);
        setProcon((short) delta);
    }

    private void setBurningAir(){
        burning = new ItemStack(Material.AIR);
    }

    private void addBurned(){
        if (burned.getType().isAir()){
            burned = new ItemStack(BurnUtils.getResult(burning.getType()));
            setBurningAir();
            return;
        }
        if (BurnUtils.getResult(burning.getType()).equals(burned.getType()) && (burned.getAmount() < 64)){
            burned = new ItemStack(burned.getType(), burned.getAmount()+1);
            setBurningAir();
        }
    }
}
