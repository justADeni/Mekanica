package justadeni.mekanica.machines.generators;

import justadeni.mekanica.machines.Machine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solar extends Machine {

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
