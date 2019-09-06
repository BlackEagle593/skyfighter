package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(private val plugin: SkyfighterPlugin) : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        plugin.wrappedPlayers.remove(event.player)
    }
}
