package ru.lni.core;

import org.bukkit.plugin.java.JavaPlugin;
import ru.lni.core.commands.HeadCommand;
import ru.lni.core.commands.LniAdminCommand;
import ru.lni.core.managers.CooldownManager;

public final class LniCore extends JavaPlugin {
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        this.cooldownManager = new CooldownManager();
        getCommand("lni").setExecutor(new LniAdminCommand());
        getCommand("head").setExecutor(new HeadCommand(cooldownManager));
        getLogger().info("LniCore (LNI Donation) activated!");
    }
}
