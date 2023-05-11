package xyz.larkyy.worldagechanger.nms_1_19_4;

import io.netty.channel.Channel;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import xyz.larkyy.worldagechanger.nmsapi.NMSHandler;


public class NMS_1_19_4 extends NMSHandler {

    private final NamespacedKey namespacedKey;

    public NMS_1_19_4(NamespacedKey namespacedKey) {
        this.namespacedKey = namespacedKey;
    }

    @Override
    public void inject(Player player) {
        Connection connection = getConnection(player);
        var pipeline = connection.channel.pipeline();

        PacketCDH cdh = new PacketCDH(player,namespacedKey);

        for (String str : pipeline.toMap().keySet()) {
            if (pipeline.get(str) instanceof Connection) {
                pipeline.addBefore("packet_handler", "WorldAgeChanger_packet_reader", cdh);
                break;
            }
        }
    }

    @Override
    public void eject(Player player) {
        Connection connection = getConnection(player);
        Channel channel = connection.channel;
        if (channel != null) {
            try {
                if (channel.pipeline().names().contains("WorldAgeChanger_packet_reader")) {
                    channel.pipeline().remove("WorldAgeChanger_packet_reader");
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void setWorldAge(Player player, long age, long time) {
        ClientboundSetTimePacket packet = new ClientboundSetTimePacket(age,time,true);
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().connection.send(packet);
    }

    private Connection getConnection(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerGamePacketListenerImpl packetListener = craftPlayer.getHandle().connection;
        try {
            var field = packetListener.getClass().getDeclaredField("h");
            field.setAccessible(true);
            return (Connection) field.get(packetListener);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
