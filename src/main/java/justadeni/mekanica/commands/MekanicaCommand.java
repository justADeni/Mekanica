package justadeni.mekanica.commands;

import justadeni.mekanica.machines.items.ItemManager;
import justadeni.mekanica.machines.utils.Coords;
import justadeni.mekanica.machines.generators.Coal;
import justadeni.mekanica.machines.utils.Storage;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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

                            case "create" -> {
                                Storage.createMachine(Coal.getNew(), Coords.fromString("World,0,0,0"));
                                //Storage.saveMachine(Coords.fromString("World,0,0,0"));
                                sender.sendMessage("Mekanica machine created");
                            }
                            case "load" -> {
                                Storage.loadAllMachines();
                                sender.sendMessage("Mekanica machine loaded");
                            }
                            default -> sender.sendMessage("wrong arguments");

                        }
                    }
                    case 2 -> {
                        if (args[0].equals("get")){
                            try {
                                ItemStack item = ItemManager.getItem(Integer.parseInt(args[1]));
                                Player p = (Player) sender;

                                if (p.getInventory().getItemInMainHand().equals(new ItemStack(Material.AIR))){
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
