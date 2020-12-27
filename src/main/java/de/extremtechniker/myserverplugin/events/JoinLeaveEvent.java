package de.extremtechniker.myserverplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Bukkit.broadcastMessage("§6Ein neuer Spieler ist dem Spiel beigetreten. Sein Name ist §4"+event.getPlayer().getDisplayName());

    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Bukkit.broadcastMessage("§6Ein Spieler hat das Spiel verlassen (Sad Emojy). Sein Name war §4"+event.getPlayer().getDisplayName());

    }

}
