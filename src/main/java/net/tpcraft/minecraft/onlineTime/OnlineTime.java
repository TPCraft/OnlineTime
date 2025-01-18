package net.tpcraft.minecraft.onlineTime;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.tpcraft.minecraft.onlineTime.command.MainCommand;
import net.tpcraft.minecraft.onlineTime.event.PlayerJoinServer;
import net.tpcraft.minecraft.onlineTime.event.PlayerLeaveServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class OnlineTime extends JavaPlugin {

    public static Map<String, Long> online = new HashMap<>();
    public static Map<String, Map<String, Object>> data = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        saveData();
        loadData();

        getServer().getPluginManager().registerEvents(new PlayerJoinServer(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveServer(), this);

        getCommand("ot").setExecutor(new MainCommand());

        long autoSaveTime = getConfig().getLong("auto-save-time");

        getServer().getScheduler().runTaskTimerAsynchronously(this, (task) -> {
            saveData();
        }, autoSaveTime * 20, autoSaveTime * 20);

        getLogger().info("OnlineTime 已启用。");
    }

    @Override
    public void onDisable() {
        saveData();

        getLogger().info("OnlineTime 已禁用。");
    }

    private void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            data = objectMapper.readValue(new File(getDataFolder() + "/data.json"), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(getDataFolder() + "/data.json"), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
