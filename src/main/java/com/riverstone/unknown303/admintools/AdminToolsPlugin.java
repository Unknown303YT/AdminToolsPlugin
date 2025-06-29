package com.riverstone.unknown303.admintools;

import com.riverstone.unknown303.admintools.item.BanHammerItem;
import com.riverstone.unknown303.admintools.item.CleansingSwordItem;
import com.riverstone.unknown303.admintools.item.ClearSwordItem;
import com.riverstone.unknown303.admintools.item.KickStaffItem;
import com.riverstone.unknown303.admintools.misc.AdminUtil;
import com.riverstone.unknown303.admintools.misc.Commands;
import com.riverstone.unknown303.admintools.misc.PlayerNBTHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminToolsPlugin extends JavaPlugin {
    private static PlayerNBTHolder playerNBT = null;

    @Override
    public void onLoad() {
        super.onLoad();

        getLogger().info("Loading AdminTools...");
        playerNBT = new PlayerNBTHolder(this);
        playerNBT.saveAll();
        getLogger().info("AdminTools loaded!");
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getLogger().info("Starting Admin Tools...");
        AdminUtil.register();
        BanHammerItem.register(this);
        KickStaffItem.register(this);
        CleansingSwordItem.register(this);
        ClearSwordItem.register(this);
        Commands.register(this);
        getLogger().info("Admin Tools started!");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        getLogger().info("Shutting down AdminTools...");
        playerNBT.saveAll();
        getLogger().info("AdminTools shut down.");
    }

    public static PlayerNBTHolder nbtHolder() {
        return playerNBT;
    }
}
