package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffectType
import kotlin.math.abs
import kotlin.math.min

class MoveListener(private val plugin: SkyfighterPlugin) : Listener {

    private val speedToStartFlying = 0.7
    private val spawnProtectionMoveOffset = 0.01

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player

        // Remove spawn protection when moving
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY) && (event.from.distanceSquared(event.to) >
                    spawnProtectionMoveOffset || event.from.yaw != event.to.yaw || event.from.pitch != event.to.pitch)
        ) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY)
            player.isFlying = false
            player.allowFlight = false
            player.isGliding = true
        }

        // Kill when below map
        if (player.location.y < 0) {
            player.health = 0.0
        }

        // Update exp bar to indicate gliding speed
        if (player.isGliding) {
            val maxSpeed = plugin.wrappedPlayers[player]?.booster!!.maxSpeed
            val speed = player.velocity.lengthSquared()
            val speedDisplay = min(speed, maxSpeed)
            player.exp = (speedDisplay / maxSpeed).toFloat()
        } else if (player.exp != 0F) {
            player.exp = 0F
        }

        // start flying when falling fast enough
        if (!player.isGliding && abs(player.velocity.y) > speedToStartFlying) {
            player.isGliding = true
        }

        // stop flying when in liquid
        if (player.isGliding && player.location.block.isLiquid) {
            player.isGliding = false
        }
    }
}
