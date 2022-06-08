package justadeni.mekanica;

import justadeni.mekanica.commands.MekanicaCommand;
import justadeni.mekanica.utils.ClassHelper;
import justadeni.mekanica.utils.files.Storage;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Mekanica extends JavaPlugin {

    private static Mekanica plugin;
    public static JavaPlugin getPlugin(){
        return plugin;
    }

    //@SneakyThrows
    @Override
    public void onEnable() {
        //Bukkit.getConsoleSender().sendMessage("Mekanica enabled");

        plugin = this;

        getCommand("mekanica").setExecutor(new MekanicaCommand());

        //getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);
        //getServer().getPluginManager().registerEvents(new BlockBreak(), plugin);

        ClassHelper.registerListeners();

        TaskScheduler.tickMachines();
        TaskScheduler.saveContinuously();
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Storage.saveAllMachines();
    }

    //use this instead of System.out.println()
    public static void log(String msg){
        plugin.getLogger().log(Level.INFO, ChatColor.translateAlternateColorCodes('&', msg));
    }
}
