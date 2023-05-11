package xyz.larkyy.worldagechanger.pluginspigot.messages;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.larkyy.worldagechanger.pluginspigot.Config;
import xyz.larkyy.worldagechanger.pluginspigot.WorldAgeChanger;

public class MessageHandler {
    private final Config config = new Config(WorldAgeChanger.instance(),"messages.yml");

    public void load() {
        config.load();
    }

    public FileConfiguration getCfg() {
        return config.getConfiguration();
    }

    public void save() {
        config.save();
    }
}
