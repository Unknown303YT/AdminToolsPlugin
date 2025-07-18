package com.riverstone.unknown303.admintools.misc;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.NBTFileHandle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerNBTHolder implements Listener {
    private static final HashMap<UUID, NBTFileHandle> holder =
            new HashMap<>();

    public static void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerNBTHolder(), plugin);
    }

    public static void modify(Player player, Consumer<NBTFileHandle> run) {
        try {
            NBTFileHandle nbt = holder.get(player.getUniqueId());
            run.accept(nbt);
            nbt.save();
        } catch (IOException e) {
            Bukkit.shutdown();
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        try {
            Player player = event.getPlayer();
            NBTFileHandle nbtFile = NBT.getFileHandle(new File(".admintools",
                    player.getUniqueId().toString()));
            player.sendMessage("AdminTools: " + nbtFile.getFile().getAbsolutePath());
            onJoin(player, nbtFile);
        } catch (IOException e) {
            Bukkit.shutdown();
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent event) {
        Commands.ANONYMOUS_ADMIN_TOOLS.forEach((id, value) ->
                holder.get(id).setBoolean("anonymous_admin_usage", value));
        save(event.getPlayer());
    }

    private static void onJoin(Player player, NBTFileHandle nbt) {
        holder.put(player.getUniqueId(), nbt);
        Commands.ANONYMOUS_ADMIN_TOOLS.put(player.getUniqueId(),
                nbt.getBoolean("anonymous_admin_usage"));
    }

    public static void saveAll() {
        holder.forEach((id, nbt) -> {
            try {
                nbt.save();
            } catch (IOException e) {
                Bukkit.shutdown();
                throw new RuntimeException(e);
            }
        });
    }

    public static void save(Player player) {
        try {
            holder.get(player.getUniqueId()).save();
        } catch (IOException e) {
            Bukkit.shutdown();
            throw new RuntimeException(e);
        }
    }
}
