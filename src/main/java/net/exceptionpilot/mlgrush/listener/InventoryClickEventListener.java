package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.MapLocations;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.sql.user.SQLPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 21:20
 * Class «» InventoryClickEventListener
 **/

public class InventoryClickEventListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        RushPlayer rushPlayer = RushPlayer.getPlayer((Player) event.getWhoClicked());
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getName() == null) return;
        if(rushPlayer.isLobby()) {
            if(event.getClickedInventory().getName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().inventoryName.get("sort"))) {
                if(event.getCurrentItem() == null) return;
                if(event.getCurrentItem().getItemMeta() == null) return;
                if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                    return;
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cAbbrechen")) {
                    rushPlayer.getPlayer().closeInventory();
                    rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast den Vorgang §aerfolgreich §7abgebrochen!");
                    rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.LEVEL_UP, 20, 20);
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aSpeichern")) {
                    Inventory inventory = event.getClickedInventory();
                    if(inventory.first(Material.WOOD_PICKAXE) != -1 && inventory.first(Material.STICK) != -1 && inventory.first(Material.SANDSTONE) != -1) {
                        int axe = inventory.first(Material.WOOD_PICKAXE) - 9;
                        int stick = inventory.first(Material.STICK) - 9;
                        int sandstone = inventory.first(Material.SANDSTONE) - 9;
                        SQLPlayer sqlPlayer = new SQLPlayer(rushPlayer.getPlayer());
                        sqlPlayer.set("STICK", stick);
                        sqlPlayer.set("PICKAXE", axe);
                        sqlPlayer.set("BLOCK", sandstone);
                        rushPlayer.getPlayer().closeInventory();
                        rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.LEVEL_UP, 20, 20);
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§7Dein Inventar wurde §aerfolgreich §7gespeichert!");
                    } else {
                        rushPlayer.getPlayer().closeInventory();
                        rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.NOTE_BASS, 20, 20);
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cEs ist ein Fehler aufgetreten!");
                    }
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}