package justadeni.mekanica.machines.generators;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter
public class Solar extends Machine {
    public final static ItemManager itemManager = new ItemManager(3,"Solar Generator", Material.DAYLIGHT_DETECTOR);

    private short production;

    public Solar(int RF, int limit, short production) {
        super(RF, limit);
        this.production = production;
    }

    @Override
    public void produce(){
        if (getRF() < getLimit()) { //checks if RF storage isn't full

            setRF(getRF() + production);

        }
    }

    public static Solar getNew(){
        return new Solar(0,20000, (short) 5);
    }
}
