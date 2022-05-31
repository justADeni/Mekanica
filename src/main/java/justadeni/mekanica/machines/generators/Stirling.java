package justadeni.mekanica.machines.generators;

import justadeni.mekanica.machines.Machine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stirling extends Machine {

    private short production;
    private int fuel;
    private byte progress;

    public Stirling( int RF, int limit, short production, int fuel, byte progress) {
        super(RF, limit);
        this.production = production;
        this.fuel = fuel;
        this.progress = progress;
    }

    @Override
    public void produce(){
        if (getRF() < getLimit()) { //checks if RF storage isn't full

            if (progress < 100) { //continues consuming fuel

                //progress = (byte) (progress + 5);
                progress += 5;

                setRF(getRF() + production);

            } else if (fuel > 0) { //draws fresh fuel

                fuel--;
                progress = 0;

                setRF(getRF() + production);

            }
        }
    }

    public static Coal getNew(){
        return new Coal(0, 80000, (short) 10, 0, (byte) 0);
    }
}
