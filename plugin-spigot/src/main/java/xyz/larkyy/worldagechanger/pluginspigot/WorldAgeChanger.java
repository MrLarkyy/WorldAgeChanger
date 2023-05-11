package xyz.larkyy.worldagechanger.pluginspigot;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.larkyy.worldagechanger.nms_1_19_4.NMS_1_19_4;
import xyz.larkyy.worldagechanger.nmsapi.NMSHandler;
import xyz.larkyy.worldagechanger.pluginspigot.commands.Commands;
import xyz.larkyy.worldagechanger.pluginspigot.messages.MessageHandler;

public final class WorldAgeChanger extends JavaPlugin {

    private static WorldAgeChanger instance;
    private MessageHandler messageHandler;
    private NMSHandler nmsHandler;
    private WorldAgeHandler worldAgeHandler;

    @Override
    public void onLoad() {
        instance = this;
        NamespacedKey namespacedKey = new NamespacedKey(this,"WORLD_AGE");
        this.messageHandler = new MessageHandler();
        messageHandler.load();
        switch (getServer().getBukkitVersion()) {
            default -> {
                nmsHandler = new NMS_1_19_4(namespacedKey);
            }
        }
        this.worldAgeHandler = new WorldAgeHandler(namespacedKey);
    }

    @Override
    public void onEnable() {
        getCommand("worldagechanger").setExecutor(new Commands());
        nmsHandler.injectAll();
        getServer().getPluginManager().registerEvents(new Listeners(),this);
    }

    @Override
    public void onDisable() {
        nmsHandler.ejectAll();
    }

    public static WorldAgeChanger instance() {
        return instance;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public WorldAgeHandler getWorldAgeHandler() {
        return worldAgeHandler;
    }

    public NMSHandler getNmsHandler() {
        return nmsHandler;
    }
}
