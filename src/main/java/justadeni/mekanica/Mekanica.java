package justadeni.mekanica;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import justadeni.mekanica.commands.MekanicaCommand;
import justadeni.mekanica.listeners.BlockBreak;
import justadeni.mekanica.listeners.BlockPlace;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.files.Storage;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public final class Mekanica extends JavaPlugin {

    private static Mekanica plugin;
    public static JavaPlugin getPlugin(){
        return plugin;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        //Bukkit.getConsoleSender().sendMessage("Mekanica enabled");

        plugin = this;

        getCommand("mekanica").setExecutor(new MekanicaCommand());

        //getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);
        //getServer().getPluginManager().registerEvents(new BlockBreak(), plugin);

        ClassHelper.registerListeners();

        TaskScheduler.tickMachines();
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Storage.saveAllMachines();
    }

    //use this instead of System.out.println()
    public static void log(String msg){
        plugin.getLogger().log(Level.INFO, msg);
    }
}
