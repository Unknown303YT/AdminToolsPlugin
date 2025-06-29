package com.riverstone.unknown303.admintools.item;

import com.riverstone.unknown303.admintools.misc.AdminUtil;
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

public class CleansingSwordItem implements Listener {
    public static final ItemStack CLEANSING_SWORD =
            new ItemStack(Material.NETHERITE_SWORD);

    public static void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new CleansingSwordItem(), plugin);
        ItemMeta meta = CLEANSING_SWORD.getItemMeta() == null ?
                Bukkit.getItemFactory().getItemMeta(Material.NETHERITE_SWORD) :
                CLEANSING_SWORD.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName("%s%sCleansing Sword".formatted(ChatColor.BOLD,
                ChatColor.GREEN));
        CLEANSING_SWORD.setItemMeta(meta);
        AdminUtil.addValidAdminTool(AdminUtil.toId(plugin, "cleansing_sword"),
                CLEANSING_SWORD);
    }

    @EventHandler
    public static void onEntityHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity entity1 = event.getDamager();
        if (entity instanceof Player target
                && entity1 instanceof Player attacker) {
            if (attacker.getInventory().getItemInMainHand().isSimilar(CLEANSING_SWORD)) {
                target.getActivePotionEffects().forEach(effect ->
                        target.removePotionEffect(effect.getType()));
                target.sendMessage("%sYour potion effects have been cleared by %s%s"
                        .formatted(ChatColor.BOLD, ChatColor.RESET,
                                AdminUtil.getAnonymousName(attacker)));
                attacker.sendMessage(ChatColor.BOLD + "You have cleared the effects of " +
                        ChatColor.RESET + target.getDisplayName());
            }
        }
    }
}
