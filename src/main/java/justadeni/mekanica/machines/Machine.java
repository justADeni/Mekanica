package justadeni.mekanica.machines;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public abstract class Machine {
    private int RF;
    private int limit;
    private short production;

    public Machine(int RF, int limit, short production) {
        this.RF = RF;
        this.limit = limit;
        this.production = production;
    }

    public abstract void produce(Location loc);

    public void addRF(int delta){
        RF += delta;
    }

    public void subtractRF(int delta){
        RF -= delta;
    }
}
