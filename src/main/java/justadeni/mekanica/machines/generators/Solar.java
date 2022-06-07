package justadeni.mekanica.machines.generators;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@Setter
public class Solar extends Machine {
    public final static ItemManager itemManager = new ItemManager(3,"Solar Generator", Material.DAYLIGHT_DETECTOR);
    public static Solar getNew(){
        return new Solar(0,20000, (short) 0);
    }

    public Solar(int RF, int limit, short production) {
        super(RF, limit, production);
    }

    @Override
    public void produce(Location loc){
        if (getRF() >= getLimit()) {
            setProcon((short) 0);
            return;
        }
        int time = (int) loc.getWorld().getTime();
        if (time < 12000){

            int delta = (int)(-0.003*Math.abs(time-6000)+20); //see https://www.desmos.com/calculator/dxskyfv6wi
            addRF(delta);
            setProcon((short) delta);
        }
    }
}
