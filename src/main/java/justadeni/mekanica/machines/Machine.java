package justadeni.mekanica.machines;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public abstract class Machine {
    private int RF;
    private int limit;
    private short procon;

    public Machine(int RF, int limit, short procon) {
        this.RF = RF;
        this.limit = limit;
        this.procon = procon;
    }

    public abstract void produce(Location loc);

    public void addRF(int delta){
        RF += delta;
    }

    public void subtractRF(int delta){
        RF -= delta;
    }
}
