package de.eaglefamily.skyfighter.listener

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent
import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawnListener(private val plugin: SkyfighterPlugin) : Listener {

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        event.respawnLocation = plugin.spawnpoint.randomSpawnpoint()
    }

    @EventHandler
    fun onPlayerPostRespawn(event: PlayerPostRespawnEvent) {
        val player = event.player
        plugin.wrappedPlayers[player]?.reset()
    }
}
