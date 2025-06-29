package com.riverstone.unknown303.admintools.item;

import com.riverstone.unknown303.admintools.misc.AdminUtil;
import com.riverstone.unknown303.admintools.misc.Commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearSwordItem implements Listener {
    public static final ItemStack CLEAR_SWORD =
            new ItemStack(Material.NETHERITE_SWORD);

    public static void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ClearSwordItem(), plugin);
        ItemMeta meta = CLEAR_SWORD.getItemMeta() == null ?
                Bukkit.getItemFactory().getItemMeta(Material.NETHERITE_SWORD) :
                CLEAR_SWORD.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName("%s%sClearing Sword".formatted(ChatColor.BOLD,
                ChatColor.GREEN));
        CLEAR_SWORD.setItemMeta(meta);
        AdminUtil.addValidCustomItemStack(AdminUtil.toId(plugin, "clear_sword"),
                CLEAR_SWORD);
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity entity1 = event.getDamager();
        if (entity instanceof Player target
                && entity1 instanceof Player attacker) {
            if (attacker.getInventory().getItemInMainHand().isSimilar(CLEAR_SWORD)) {
                target.getInventory().clear();
                target.sendMessage("%sYour inventory has been cleared by %s%s"
                        .formatted(ChatColor.BOLD, ChatColor.RESET,
                                AdminUtil.getAnonymousName(attacker)));
                attacker.sendMessage(ChatColor.BOLD + "You have cleared the inventory of " +
                        ChatColor.RESET + target.getDisplayName());
            }
        }
    }
}
