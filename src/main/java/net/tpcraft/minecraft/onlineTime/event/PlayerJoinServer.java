package net.tpcraft.minecraft.onlineTime.event;

import net.tpcraft.minecraft.onlineTime.OnlineTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinServer implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString().replaceAll("-", "");

        OnlineTime.online.put(uuid, System.currentTimeMillis());
    }

}
