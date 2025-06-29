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

public class KickStaffItem implements Listener {
    public static final ItemStack KICK_STAFF = new ItemStack(Material.NETHERITE_AXE);

    public static void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new KickStaffItem(), plugin);
        ItemMeta meta = KICK_STAFF.getItemMeta() == null ?
                Bukkit.getItemFactory().getItemMeta(Material.NETHERITE_AXE) :
                KICK_STAFF.getItemMeta();
        meta.setUnbreakable(true);
        meta.setAttributeModifiers(AdminUtil.ADMIN_TOOLS_MODIFIERS);
        meta.setCustomModelData(34);
        meta.setDisplayName("%s%sKick Staff".formatted(ChatColor.BOLD, ChatColor.YELLOW));
        KICK_STAFF.setItemMeta(meta);
        AdminUtil.addValidCustomItemStack(AdminUtil.toId(plugin, "kick_staff"),
                KICK_STAFF);
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity entity1 = event.getDamager();
        if (entity instanceof Player target &&
                entity1 instanceof Player attacker) {
            if (attacker.getInventory().getItemInMainHand().isSimilar(KICK_STAFF)) {
                target.getInventory().clear();
                target.kickPlayer(kickMessage(attacker));
            }
        }
    }

    private String kickMessage(Player attacker) {
        return "%s%sYou have been kicked by %s%s".formatted(ChatColor.BOLD,
                ChatColor.WHITE, ChatColor.RESET, AdminUtil.getAnonymousName(attacker));
    }
}
