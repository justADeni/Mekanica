package justadeni.mekanica.commands;

import justadeni.mekanica.machines.utils.Coords;
import justadeni.mekanica.machines.generators.Coal;
import justadeni.mekanica.machines.utils.Storage;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MekanicaCommand implements CommandExecutor {

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mekanica")){

            switch (args.length) {
                case 0 -> sender.sendMessage("insufficient arguments");
                case 1 -> {
                    switch (args[0]) {

                        case "create" -> {
                            Storage.createMachine(Coal.getNew(), Coords.fromString("World,0,0,0"));
                            //Storage.saveMachine(Coords.fromString("World,0,0,0"));
                            sender.sendMessage("Mekanica machines created");
                        }
                        case "load" -> {
                            Storage.loadAllMachines();
                            sender.sendMessage("Mekanica machines loaded");
                        }
                        default -> sender.sendMessage("wrong arguments");

                    }
                }
                default -> sender.sendMessage("too many arguments");
            }
        }
        return true;

    }


    }
