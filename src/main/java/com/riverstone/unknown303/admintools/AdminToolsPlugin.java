package com.riverstone.unknown303.admintools;

import com.riverstone.unknown303.admintools.item.BanHammerItem;
import com.riverstone.unknown303.admintools.item.CleansingSwordItem;
import com.riverstone.unknown303.admintools.item.ClearSwordItem;
import com.riverstone.unknown303.admintools.item.KickStaffItem;
import com.riverstone.unknown303.admintools.misc.AdminUtil;
import com.riverstone.unknown303.admintools.misc.Commands;
import com.riverstone.unknown303.admintools.misc.PlayerNBTHolder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminToolsPlugin extends JavaPlugin {
    @Override
    public void onLoad() {
        super.onLoad();
        getLogger().info("AdminTools loaded!");
    }

    @Override
    public void onEnable() {
        super.onEnable();

        AdminUtil.register();
        BanHammerItem.register(this);
        KickStaffItem.register(this);
        CleansingSwordItem.register(this);
        ClearSwordItem.register(this);
        Commands.register(this);
        PlayerNBTHolder.register(this);
        PlayerNBTHolder.saveAll();
        getLogger().info("Admin Tools enabled!");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        PlayerNBTHolder.saveAll();
        getLogger().info("AdminTools disabled.");
    }
}
