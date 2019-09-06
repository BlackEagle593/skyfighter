package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import de.eaglefamily.skyfighter.WrappedPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(private val plugin: SkyfighterPlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        plugin.wrappedPlayers[player] =
            WrappedPlayer(plugin = plugin, player = player)
    }
}
