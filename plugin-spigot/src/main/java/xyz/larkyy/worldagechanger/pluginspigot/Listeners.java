package xyz.larkyy.worldagechanger.pluginspigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        WorldAgeChanger.instance().getNmsHandler().inject(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        WorldAgeChanger.instance().getNmsHandler().eject(event.getPlayer());
    }

}
