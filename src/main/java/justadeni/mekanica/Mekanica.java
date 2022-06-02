package justadeni.mekanica;

import justadeni.mekanica.commands.MekanicaCommand;
import justadeni.mekanica.listeners.BlockPlace;
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
        Bukkit.getConsoleSender().sendMessage("Mekanica enabled");

        plugin = this;

        getCommand("mekanica").setExecutor(new MekanicaCommand());

        getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);



        //Storage.loadAllMachines();

        /*
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                Storage.saveMachines();
            }
        }.runTaskTimerAsynchronously(plugin, 0,5*60*20);
        */
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
