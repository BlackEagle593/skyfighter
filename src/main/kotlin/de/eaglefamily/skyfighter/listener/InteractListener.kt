package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffectType

class InteractListener(private val plugin: SkyfighterPlugin) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        plugin.wrappedPlayers[player]?.weapon!!.shoot()

        // Remove spawn protection when interacting
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY)
            player.isFlying = false
            player.allowFlight = false
            player.isGliding = true
        }
    }
}
