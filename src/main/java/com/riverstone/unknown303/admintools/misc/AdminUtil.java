package com.riverstone.unknown303.admintools.misc;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
//import dev.jorel.commandapi.arguments.ArgumentSuggestions;
//import dev.jorel.commandapi.arguments.CustomArgument;
//import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class AdminUtil {
    public static final String ADMIN_NAME = ChatColor.RED + "LeagueCraftAdmin";

    public static final Multimap<Attribute, AttributeModifier> ADMIN_TOOLS_MODIFIERS =
            ArrayListMultimap.create();
    public static final AttributeModifier ADMIN_TOOLS_MODIFIER =
            new AttributeModifier(UUID.fromString("40e32752-41a2-4593-8aed-b5c909f6df71"),
                    "admin_tools_damage", Double.MAX_VALUE,
                    AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

    private static final HashMap<String, ItemStack> VALID_STACKS =
            new HashMap<>();

    public static final CustomArgument<ItemStack, String> CUSTOM_ITEM_STACKS_ARGUMENT =
            (CustomArgument<ItemStack, String>) new CustomArgument<>(new StringArgument("item"), info ->
                    VALID_STACKS.get(info.currentInput().replaceAll("" + '"',
                            "")))
                    .replaceSuggestions(ArgumentSuggestions.strings(
                            VALID_STACKS.keySet()));

    public static void register() {
        ADMIN_TOOLS_MODIFIERS.put(Attribute.GENERIC_ATTACK_DAMAGE, ADMIN_TOOLS_MODIFIER);
    }

    public static void setDisplayName(Player player, String name) {
        player.setDisplayName(name);
        player.setPlayerListName(name);
    }

    public static void addValidCustomItemStack(String id, ItemStack itemStack) {
        VALID_STACKS.put(id, itemStack);
    }

    public static void addAllValidCustomItemStacks(Map<String, ItemStack> validStacks) {
        VALID_STACKS.putAll(validStacks);
    }

    public static String toId(JavaPlugin plugin, String path) {
        return "%s:%s".formatted(plugin.getName().toLowerCase(), path);
    }

    public static String getAnonymousName(Player player) {
        return Commands.ANONYMOUS_ADMIN_TOOLS.get(player.getUniqueId()) ?
                ADMIN_NAME : player.getName();
    }
}
