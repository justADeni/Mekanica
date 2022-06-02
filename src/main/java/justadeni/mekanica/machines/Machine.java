package justadeni.mekanica.machines;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Machine {


    private int RF;
    private int limit;

    public Machine(int RF, int limit) {
        this.RF = RF;
        this.limit = limit;
    }

    public abstract void produce();

}
