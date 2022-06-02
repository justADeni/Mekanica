package justadeni.mekanica.commands;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.utils.Coords;
import justadeni.mekanica.machines.generators.Coal;
import justadeni.mekanica.utils.Storage;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MekanicaCommand implements CommandExecutor {

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mekanica")){
            if (sender instanceof Player) {
                switch (args.length) {
                    case 0 -> sender.sendMessage("insufficient arguments");
                    case 1 -> {
                        switch (args[0]) {

                            case "saveall" -> {
                                Storage.saveAllMachines();
                                sender.sendMessage("Mekanica machines saved");
                            }
                            case "loadall" -> {
                                Storage.loadAllMachines();
                                sender.sendMessage("Mekanica machines loaded");
                            }
                            default -> sender.sendMessage("wrong arguments");
                        }
                    }
                    case 2 -> {
                        if (args[0].equals("get")){
                            try {
                                ItemStack item = ItemManager.getItem(Integer.parseInt(args[1]));
                                Player p = (Player) sender;

                                if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {

                                    p.getInventory().setItemInMainHand(item);
                                    sender.sendMessage("Mekanica item recieved");
                                } else {
                                    sender.sendMessage("full main hand");
                                }

                            } catch (Error e){
                                sender.sendMessage("invalid id");
                            }
                        }
                    }
                    default -> sender.sendMessage("too many arguments");
                }
            }
        }
        return true;

    }


    }
