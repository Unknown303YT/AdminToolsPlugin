package com.riverstone.unknown303.admintools.item;

import com.riverstone.unknown303.admintools.misc.AdminUtil;
import com.riverstone.unknown303.admintools.misc.Commands;
import org.bukkit.BanList;
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

public class BanHammerItem implements Listener {
    public static final ItemStack BAN_HAMMER = new ItemStack(Material.NETHERITE_AXE);

    public static void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new BanHammerItem(), plugin);
        ItemMeta meta = BAN_HAMMER.getItemMeta() == null ?
                Bukkit.getItemFactory().getItemMeta(Material.NETHERITE_AXE) :
                BAN_HAMMER.getItemMeta();
        meta.setUnbreakable(true);
        meta.setAttributeModifiers(AdminUtil.ADMIN_TOOLS_MODIFIERS);
        meta.setCustomModelData(17);
        meta.setDisplayName("%s%sBan Hammer".formatted(ChatColor.BOLD, ChatColor.RED));
        BAN_HAMMER.setItemMeta(meta);
        AdminUtil.addValidCustomItemStack(AdminUtil.toId(plugin, "ban_hammer"),
                BAN_HAMMER);
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity entity1 = event.getDamager();
        if (entity instanceof Player target &&
                entity1 instanceof Player attacker) {
            if (attacker.getInventory().getItemInMainHand().isSimilar(BAN_HAMMER)) {
                target.getInventory().clear();
                banPlayer(target, attacker);
            }
        }
    }

    private void banPlayer(Player target, Player attacker) {
        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(),
                banMessage(attacker), null, AdminUtil.getAnonymousName(attacker));
        target.kickPlayer(banMessage(attacker));
    }

    private String banMessage(Player attacker) {
        String firstLine = "You have been banned by %s%s".formatted(ChatColor.RESET,
                AdminUtil.getAnonymousName(attacker));
        return "%s%s%s\n%s%sThe Ban Hammer has spoken!".formatted(ChatColor.BOLD,
                ChatColor.RED, firstLine, ChatColor.RESET, ChatColor.WHITE);
    }
}
