package dreamys.studio.staffessentials;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Commands implements CommandExecutor {
    private static HashMap<String, Integer> reportedPlayer = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.log("Only players can use that command!");
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (args.length > 0) {
                Utils.send(player, "Healed [" + Bukkit.getPlayer(args[0]) +  "] for [" + Math.round((Bukkit.getPlayer(args[0]).getMaxHealth() - Bukkit.getPlayer(args[0]).getHealth())) + "] hearts");
                Bukkit.getPlayer(args[0]).setHealth(Bukkit.getPlayer(args[0]).getMaxHealth());
            } else {
                Utils.send(player, "Healed yourself for [" + Math.round(player.getMaxHealth() - player.getHealth()) + "] hearts");
                player.setHealth(player.getMaxHealth());
            }
        }
        if (cmd.getName().equalsIgnoreCase("feed")) {
            if (args.length > 0) {
                Utils.send(player, "Fed [" + Bukkit.getPlayer(args[0]) +  "]");
                Bukkit.getPlayer(args[0]).setFoodLevel(20);
            } else {
                Utils.send(player, "Fed yourself");
                player.setFoodLevel(20);
            }
        }
        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (player.isOp()) {
                boolean vanished = Utils.getConfig().getBoolean("Staff." + player.getUniqueId() + ".Vanished", false);
                if (!vanished) {
                    Utils.getConfig().set("Staff." + player.getUniqueId() + ".Vanished", true);
                    Utils.saveConfig();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.hidePlayer(player);
                    }
                    Utils.send(player, "§aVanished!");
                } else {
                    Utils.getConfig().set("Staff." + player.getUniqueId() + ".Vanished", false);
                    Utils.saveConfig();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showPlayer(player);
                    }
                    Utils.send(player, "§cRevealed!");
                }
            } else {
                Utils.send(player, "§cSorry, only staff have access to that command!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("report")) {
            if (args.length >= 2) {
                reportedPlayer.putIfAbsent(args[0].toLowerCase(), 0);
                reportedPlayer.put(args[0].toLowerCase(), reportedPlayer.get(args[0].toLowerCase()) + 1);
                Utils.tellStaff(player.getDisplayName() + " reported " + args[0].toLowerCase() + " for: [" + StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " | ") + "] (Count: " + reportedPlayer.get(args[0].toLowerCase()) + ")", ChatColor.GREEN, "Teleport to " + args[0], "/tp " + args[0]);
                player.sendMessage("§aYour report has been submitted!");
            }
        }
        return false;
    }
}
