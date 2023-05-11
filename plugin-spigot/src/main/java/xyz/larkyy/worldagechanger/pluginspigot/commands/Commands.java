package xyz.larkyy.worldagechanger.pluginspigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.larkyy.worldagechanger.pluginspigot.commands.impl.GetCommand;
import xyz.larkyy.worldagechanger.pluginspigot.commands.impl.SetCommand;

import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {

    private final Map<String, ICommand> availableCommands = new HashMap<>(){
        {
            put("set",new SetCommand());
            put("get",new GetCommand());
        }
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            return false;
        }

        ICommand cmd = availableCommands.get(args[0]);
        if (cmd == null) {
            return false;
        }
        cmd.run(sender,args);
        return true;
    }
}
