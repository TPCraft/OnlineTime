package net.tpcraft.minecraft.onlineTime.event;

import net.tpcraft.minecraft.onlineTime.OnlineTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerLeaveServer implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString().replaceAll("-", "");
        String name = event.getPlayer().getName();

        Long online = OnlineTime.online.get(uuid);
        if (online == null) {
            return;
        }

        Map<String, Object> data = OnlineTime.data.get(uuid);
        if (data == null) {
            data = new HashMap<>();
            data.put("name", name);
            data.put("time", 0L);
        }

        data.put("time", Long.parseLong(String.valueOf(data.get("time"))) + (System.currentTimeMillis() - online));

        OnlineTime.data.put(uuid, data);
        OnlineTime.online.remove(uuid);
    }

}
