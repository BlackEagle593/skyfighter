package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleGlideEvent

class GlideListener(private val plugin: SkyfighterPlugin) : Listener {

    private val speedToStopFlying = 0.2
    private val slowDownMultiplier = 0.99

    @EventHandler
    fun onEntityToggleGlide(event: EntityToggleGlideEvent) {
        if (event.entity !is Player) return
        val player = event.entity as Player

        // player cannot start gliding himself
        if (event.isGliding) event.isCancelled = true

        // don't stop gliding when the player is fast enough on ground but slow him down
        val speedXZ = player.velocity.setY(0).lengthSquared()
        if (player.isGliding && !event.isGliding && speedXZ > speedToStopFlying) {
            event.isCancelled = true
            player.velocity = player.velocity.multiply(slowDownMultiplier)
        } else {
            // gliding stopped because to slow
            plugin.wrappedPlayers[player]?.booster!!.addBoost()
        }
    }
}
