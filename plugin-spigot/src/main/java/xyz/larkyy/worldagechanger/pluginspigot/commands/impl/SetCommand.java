package xyz.larkyy.worldagechanger.pluginspigot.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.larkyy.worldagechanger.pluginspigot.WorldAgeChanger;
import xyz.larkyy.worldagechanger.pluginspigot.commands.ICommand;
import xyz.larkyy.worldagechanger.pluginspigot.messages.Messages;

public class SetCommand implements ICommand {
    @Override
    public void run(CommandSender sender, String[] args) {

        if (args.length < 3) {
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            return;
        }

        long val;
        try {
            val = Long.parseLong(args[2]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage("§cUnknown number format");
            return;
        }

        Messages.AGE_SET.replace("%player%",target.getName()).replace("%value%",val+"").send(sender);
        WorldAgeChanger.instance().getWorldAgeHandler().setAge(target,val);
    }
}
