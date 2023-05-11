package xyz.larkyy.worldagechanger.nmsapi;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class NMSHandler {

    public abstract void inject(Player player);
    public abstract void eject(Player player);

    public abstract void setWorldAge(Player player, long age, long time);
    public void injectAll() {
        Bukkit.getOnlinePlayers().forEach(this::inject);
    }

    public void ejectAll() {
        Bukkit.getOnlinePlayers().forEach(this::eject);
    }

}
