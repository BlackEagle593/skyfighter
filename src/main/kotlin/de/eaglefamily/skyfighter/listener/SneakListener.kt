package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

class SneakListener(private val plugin: SkyfighterPlugin) : Listener {

    @EventHandler
    fun onPlayerToggleSneak(event: PlayerToggleSneakEvent) {
        val player = event.player
        if (!event.isSneaking) return
        plugin.wrappedPlayers[player]?.booster!!.boost()
    }
}
