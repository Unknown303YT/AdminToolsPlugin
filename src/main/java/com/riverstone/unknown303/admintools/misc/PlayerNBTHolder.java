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
    private final JavaPlugin plugin;
    private final HashMap<UUID, NBTFileHandle> holder;

    public PlayerNBTHolder(JavaPlugin plugin) {
        this.plugin = plugin;
        holder = new HashMap<>();
        Object obj = new Object();
        new Thread(() -> {
            synchronized (obj) {
                while (true) {
                    if (plugin.isEnabled())
                        break;
                }
                plugin.getServer().getPluginManager().registerEvents(this, plugin);
            }
        }).start();
    }

    public void modify(Player player, Consumer<NBTFileHandle> run) {
        try {
            NBTFileHandle nbt = holder.get(player.getUniqueId());
            run.accept(nbt);
            nbt.save();
        } catch (IOException e) {
            Bukkit.shutdown();
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            Player player = event.getPlayer();
            NBTFileHandle nbtFile = NBT.getFileHandle(new File("players",
                    player.getUniqueId().toString()));
            onJoin(player, nbtFile);
        } catch (IOException e) {
            Bukkit.shutdown();
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        try {
            holder.get(event.getPlayer().getUniqueId()).save();
            Commands.ANONYMOUS_ADMIN_TOOLS.forEach((id, value) ->
                    holder.get(id).setBoolean("anonymous_admin_usage", value));
        } catch (IOException e) {
            Bukkit.shutdown();
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new RuntimeException(e);
        }
    }

    private void onJoin(Player player, NBTFileHandle nbt) {
        holder.put(player.getUniqueId(), nbt);
        Commands.ANONYMOUS_ADMIN_TOOLS.put(player.getUniqueId(),
                nbt.getBoolean("anonymous_admin_usage"));
    }

    public void saveAll() {
        holder.forEach((id, nbt) -> {
            try {
                nbt.save();
            } catch (IOException e) {
                Bukkit.shutdown();
                Bukkit.getPluginManager().disablePlugin(plugin);
                throw new RuntimeException(e);
            }
        });
    }

    public void save(Player player) {
        try {
            holder.get(player.getUniqueId()).save();
        } catch (IOException e) {
            Bukkit.shutdown();
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new RuntimeException(e);
        }
    }
}
