package justadeni.mekanica;

import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.FileNotFoundException;

public class TaskScheduler {

    public static void startupIndex() {
        new BukkitRunnable() {
            @Override
            public void run () {

                ClassHelper.indexClassesItems();

            }
        }.runTaskAsynchronously(Mekanica.getPlugin());
    }

    public static void tickMachines() {
        new BukkitRunnable() {
            @Override
            public void run () {
                //methods
                for (Location loc : Storage.getAllMachines().keySet()){
                    Machine machine = Storage.getMachine(loc);
                    machine.produce(loc);
                }
            }
        }.runTaskTimerAsynchronously(Mekanica.getPlugin(), 20,20);
    }

    public static void saveContinuously(){
        new BukkitRunnable() {
            @Override
            public void run () {
                //methods
                Storage.saveAllMachines();
            }
        }.runTaskTimerAsynchronously(Mekanica.getPlugin(), 5*60*20,5*60*20);
    }

    public static void chunkLoad(String worldName, int chunkX, int chunkZ) {
        new BukkitRunnable() {
            @Override
            public void run() {
                //methods
                try {
                    Storage.loadChunkMachines(worldName, chunkX, chunkZ);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(Mekanica.getPlugin());
    }

    public static void chunkUnload(String worldName, int chunkX, int chunkZ){
        new BukkitRunnable() {
            @Override
            public void run() {
                //methods
                try {
                    Storage.saveAndRemoveChunkMachines(worldName, chunkX, chunkZ);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(Mekanica.getPlugin());
    }
}
