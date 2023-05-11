package xyz.larkyy.worldagechanger.nms_1_19_4;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PacketCDH extends ChannelDuplexHandler {

    private final Player player;
    private final NamespacedKey namespacedKey;

    public PacketCDH(Player player, NamespacedKey namespacedKey) {
        this.player = player;
        this.namespacedKey = namespacedKey;
    }
    @Override
    public void write(ChannelHandlerContext ctx, Object packetObj, ChannelPromise promise) throws Exception {
        if (!(packetObj instanceof Packet<?> pkt)) {
            super.write(ctx,packetObj,promise);
            return;
        }

        var name = pkt.getClass().getSimpleName();
        if (name.equalsIgnoreCase("PacketPlayOutUpdateTime")) {
            ClientboundSetTimePacket packet = (ClientboundSetTimePacket) pkt;
            var time = packet.getDayTime();
            var age = getAge(player);
            if (age == -1) {
                age = packet.getGameTime();
            }
            packetObj = new ClientboundSetTimePacket(
                    age,
                    time,
                    true
            );
        }
        super.write(ctx, packetObj, promise);
    }

    private long getAge(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (pdc.has(namespacedKey, PersistentDataType.LONG)) {
            Long val = pdc.get(namespacedKey,PersistentDataType.LONG);
            if (val == null) {
                return -1;
            }
            return val;
        }
        return -1;
    }
}
