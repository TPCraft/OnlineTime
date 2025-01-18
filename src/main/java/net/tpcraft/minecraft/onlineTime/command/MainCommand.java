package net.tpcraft.minecraft.onlineTime.command;

import net.tpcraft.minecraft.onlineTime.OnlineTime;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage("[OnlineTime] 此命令仅能玩家使用");

            return false;
        }

        Player player = (Player) commandSender;
        String uuid = player.getUniqueId().toString().replaceAll("-", "");

        Map<String, Object> data = OnlineTime.data.get(uuid);

        String thisTime = String.format("%.2f", (System.currentTimeMillis() - OnlineTime.online.get(uuid)) / 1000.0 / 60 / 60);
        String countTime = (data == null ? "0.00" : String.format("%.2f", Long.parseLong(String.valueOf(data.get("time"))) / 1000.0 / 60 / 60));

        commandSender.sendMessage(
                "",
                "§7====================",
                "",
                " §6§lOnlineTime",
                "",
                " §7本次游戏时长: §2" + thisTime + " §7小时",
                " §7累计游戏时长: §2" + countTime + " §7小时",
                "",
                "§7====================",
                ""
        );

        return false;
    }

}
