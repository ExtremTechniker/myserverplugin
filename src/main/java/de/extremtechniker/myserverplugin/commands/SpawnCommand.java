package de.extremtechniker.myserverplugin.commands;

import de.extremtechniker.myserverplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

public class SpawnCommand implements CommandExecutor {

    private HashMap<String,Integer> timout = new HashMap();


    public SpawnCommand(Main plugin) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, () -> {
            for(Map.Entry<String, Integer> me : timout.entrySet()) {
                if(me.getValue() > 0) {
                    timout.replace(me.getKey(),me.getValue()-1);
                } else if (me.getValue() == 0) {
                    timout.remove(me.getKey());
                }
            }
        },0 , 20 * 1); // 20 Tick * 1 Sekunden => 20 Tick/Sek
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            FileConfiguration config = Main.getPlugin().getConfig();

            if(args.length == 0) { // /spawn
                //Teleport player to cords in config

                if(timout.get(player.getName()) != null ) {
                    player.sendMessage("§Langsam! Du bist viel zuschnell!");
                    timout.put(player.getName(),9); // All ten seconds
                    return false;
                }
                timout.put(player.getName(),9); // All ten seconds


                if(Main.getPlugin().getConfig().get("teleport.spawn") != null) {

                    World world = Bukkit.getWorld(config.getString("teleport.spawn.world"));
                    double x = config.getDouble("teleport.spawn.x");
                    double y = config.getDouble("teleport.spawn.y");
                    double z = config.getDouble("teleport.spawn.z");
                    float yaw = (float) config.getDouble("teleport.spawn.yaw");
                    float pitch = (float) config.getDouble("teleport.spawn.pitch");
                    Location loc = new Location(world,x,y,z,yaw,pitch);

                    player.teleport(loc);
                    player.sendMessage("§aDu bist nun am Spawn");
                    return true;
                }

            } else if(args.length == 1 && args[0].equalsIgnoreCase("set")) { // /spawn set
                if(player.hasPermission("myserverplugin.spawn.setSpawn")) {
                    Location loc = player.getLocation();
                    config.set("teleport.spawn.world",loc.getWorld().getName());
                    config.set("teleport.spawn.x",loc.getBlockX());
                    config.set("teleport.spawn.y",loc.getBlockY());
                    config.set("teleport.spawn.z",loc.getBlockZ());
                    config.set("teleport.spawn.yaw",loc.getYaw());
                    config.set("teleport.spawn.pitch",loc.getPitch());
                    Main.getPlugin().saveConfig();
                    player.sendMessage("Der Spawn wurde an deine Stelle gesetzt");
                }
            }

        }

        return false;
    }
}
