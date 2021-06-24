package dreamys.studio.staffessentials;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {
    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (player.getItemInHand().equals(ItemsList.banWand())) {
                if (event.getEntity() instanceof Player) {
                    Player bad = (Player) event.getEntity();
                    bad.setBanned(true);
                    bad.kickPlayer("Banned by " + player.getName());
                    Bukkit.broadcastMessage("§c" + bad.getName() + "Has been §4permanently banned by §b" + player.getName() + "§c using [Wand of Banning]");
                }
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.getItemInHand().hasItemMeta() || event.getItem() == null) return; //Prevent NPE
        if (player.getItemInHand().equals(ItemsList.vanishPotion())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
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
                event.setCancelled(true);
            }
        }
        if (player.getItemInHand().equals(ItemsList.clearInv())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                player.getInventory().clear();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().equals(ItemsList.banWand())) e.setCancelled(true);
        if (e.getItemDrop().getItemStack().equals(ItemsList.vanishPotion())) e.setCancelled(true);
        if (e.getItemDrop().getItemStack().equals(ItemsList.clearInv())) e.setCancelled(true);
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        if (e.getPlayer().isOp()) {
            if (Utils.getConfig().getString("Staff." + e.getPlayer().getUniqueId() + ".Chat", "all").equals("staff")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.isOp()) Utils.send(player, e.getPlayer().getName() + ": " + e.getMessage());
                }
                e.setCancelled(true);
            }
        }
    }
}
