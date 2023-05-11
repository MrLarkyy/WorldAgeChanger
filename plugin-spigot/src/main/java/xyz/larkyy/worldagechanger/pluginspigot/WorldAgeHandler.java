package xyz.larkyy.worldagechanger.pluginspigot;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class WorldAgeHandler {

    private final NamespacedKey namespacedKey;

    public WorldAgeHandler(NamespacedKey namespacedKey) {
        this.namespacedKey = namespacedKey;
    }

    public void setAge(Player player,long age) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        WorldAgeChanger.instance().getNmsHandler().setWorldAge(player,age, player.getPlayerTime());
        pdc.set(namespacedKey, PersistentDataType.LONG,age);
    }

    public long getAge(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (pdc.has(namespacedKey,PersistentDataType.LONG)) {
            Long val = pdc.get(namespacedKey,PersistentDataType.LONG);
            if (val == null) {
                return 0;
            }
            return val;
        }
        return 0;
    }

}
