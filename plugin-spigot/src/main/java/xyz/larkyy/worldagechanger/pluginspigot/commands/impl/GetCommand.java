package xyz.larkyy.worldagechanger.pluginspigot.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.larkyy.worldagechanger.pluginspigot.WorldAgeChanger;
import xyz.larkyy.worldagechanger.pluginspigot.commands.ICommand;
import xyz.larkyy.worldagechanger.pluginspigot.messages.Messages;

public class GetCommand implements ICommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            return;
        }

        long val = WorldAgeChanger.instance().getWorldAgeHandler().getAge(target);

        Messages.AGE_GET.replace("%player%",target.getName()).replace("%value%",val+"").send(sender);
    }
}
