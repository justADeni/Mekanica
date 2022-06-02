package justadeni.mekanica.machines.generators;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter
public class Coal extends Machine{

    public final static ItemManager itemManager = new ItemManager(1,"Coal Generator", Material.DROPPER);
    public static Coal getNew(){
        return new Coal(0, 80000, (short) 20, 0, (byte) 0);
    }
    private short production;
    private int fuel;
    private byte progress;

    public Coal( int RF, int limit, short production, int fuel, byte progress) {
        super(RF, limit);
        this.production = production;
        this.fuel = fuel;
        this.progress = progress;
    }

    @Override
    public void produce() {
        if (getRF() < getLimit()) { //checks if RF storage isn't full

            if (progress < 100) { //continues consuming fuel

                //progress = (byte) (progress + 10);
                progress += 10;

                setRF(getRF() + production);

            } else if (fuel > 0) { //draws fresh fuel

                fuel--;
                progress = 0;

                setRF(getRF() + production);

            }
        }
    }


}
