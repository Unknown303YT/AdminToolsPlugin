package com.riverstone.unknown303.admintools.misc;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Commands {
    public static final HashMap<UUID, Boolean> ANONYMOUS_ADMIN_TOOLS =
            new HashMap<>();

    public static final CommandAPICommand ANONYMOUS_ADMIN_TOOLS_COMMAND =
            new CommandAPICommand("setAnonymousAdminTools")
                    .withArguments(new BooleanArgument("value"))
                    .withOptionalArguments(new PlayerArgument("target"))
                    .withAliases("anonymousAdminTools", "maskedAdminTools",
                            "setMaskedAdminTools")
                    .withPermission(CommandPermission.OP)
                    .executesPlayer((player, args) -> {
                        Boolean value = (Boolean) args.get(0);
                        Player target = (Player) args.get(1);
                        ANONYMOUS_ADMIN_TOOLS.put(
                                Objects.requireNonNullElse(target, player).getUniqueId(),
                                value);
                    });

    public static final CommandAPICommand GET_ADMIN_TOOLS_COMMAND =
            new CommandAPICommand("getAdminTools")
                    .withArguments(AdminUtil.ADMIN_TOOL_ITEM_ARGUMENT)
                    .withAliases("getAdminTool", "adminTool", "giveAdmin",
                            "giveAdminTool", "giveAdminTools")
                    .withPermission(CommandPermission.OP)
                    .executesPlayer((player, args) -> {
                        player.getInventory().addItem((ItemStack) args.get("item"));
                    });

    public static void register(JavaPlugin plugin) {
        ANONYMOUS_ADMIN_TOOLS_COMMAND.register(plugin);
        GET_ADMIN_TOOLS_COMMAND.register(plugin);
    }
}
