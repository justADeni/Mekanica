package justadeni.mekanica;

import justadeni.mekanica.commands.MekanicaCommand;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Mekanica extends JavaPlugin {

    private static Mekanica plugin;
    public static JavaPlugin getPlugin(){
        return plugin;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("Mekanica enabled");

        plugin = this;

        /*
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                Storage.saveMachines();
            }
        }.runTaskTimerAsynchronously(plugin, 0,5*60*20);
        */

        getCommand("mekanica").setExecutor(new MekanicaCommand());

        //Storage.loadAllMachines();
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //use this instead of System.out.println()
    public static void log(String msg){
        plugin.getLogger().log(Level.INFO, msg);
    }
}
