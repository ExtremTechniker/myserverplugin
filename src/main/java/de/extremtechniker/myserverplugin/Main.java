package de.extremtechniker.myserverplugin;

import de.extremtechniker.myserverplugin.commands.SpawnCommand;
import de.extremtechniker.myserverplugin.events.JoinLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        init(Bukkit.getPluginManager());
        System.out.println(Util.PREFIX + "Plugin loaded successfully");
    }

    @Override
    public void onDisable() {
        System.out.println(Util.PREFIX + "Plugin stopped successfully");
    }

    private void init(PluginManager pm) {
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        pm.registerEvents(new JoinLeaveEvent(),this);
    }

    public static Main getPlugin() {
        return plugin;
    }

}
