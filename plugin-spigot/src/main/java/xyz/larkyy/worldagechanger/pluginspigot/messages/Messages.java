package xyz.larkyy.worldagechanger.pluginspigot.messages;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.larkyy.worldagechanger.pluginspigot.WorldAgeChanger;

public enum Messages {

    NO_PERMISSION("no-permission","&cYou have no permissions to do that!"),
    AGE_SET("age-set","&aWorld age has been set to %player% with value of %value%!"),
    AGE_GET("age-get","&aWorld age of %player% is %value%");
    private final String path;
    private final Object defVal;

    Messages(String path, Object defVal) {
        this.path = path;
        this.defVal = defVal;
    }

    public Message replace(String s1, String s2) {
        return get().replace(s1,s2);
    }

    public Message get() {
        if (getMsgFile().contains(path)) {
            return new Message(getMsgFile().get(path));
        } else {
            getMsgFile().set(path,defVal);
            WorldAgeChanger.instance().getMessageHandler().save();
            return new Message(defVal);
        }
    }

    private FileConfiguration getMsgFile() {
        return WorldAgeChanger.instance().getMessageHandler().getCfg();
    }

    public void send(CommandSender sender) {
        if (getMsgFile().contains(path)) {
            new Message(getMsgFile().get(path)).send(sender);
        } else {
            new Message(defVal).send(sender);
        }
    }
}
