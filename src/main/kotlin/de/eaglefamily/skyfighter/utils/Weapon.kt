package de.eaglefamily.skyfighter.utils

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class Weapon(private val plugin: SkyfighterPlugin, private val player: Player) {

    private val shootDelay = 5L
    private var shootDelayActive = false
    private val arrowSpeed = 6.0
    private val arrowSize = 3.0
    private val arrowDamage = 4.0

    fun shoot() {
        if (player.gameMode == GameMode.CREATIVE) return
        if (shootDelayActive) return
        if (!player.isGliding) return
        if (player.inventory.itemInMainHand != plugin.items.bow) return
        shootDelayActive = true
        val arrowVelocity = player.eyeLocation.direction.normalize().multiply(arrowSpeed)
        val arrow = player.launchProjectile(Arrow::class.java, arrowVelocity)
        arrow.setBounce(false)
        arrow.damage = arrowDamage
        customArrowHitbox(arrow)

        object : BukkitRunnable() {
            override fun run() {
                shootDelayActive = false
            }
        }.runTaskLater(plugin, shootDelay)
    }

    private fun customArrowHitbox(arrow: Arrow) {
        object : BukkitRunnable() {
            override fun run() {
                if (arrow.isOnGround || arrow.isDead) {
                    arrow.remove()
                    this.cancel()
                    return
                }

                val nearbyEntities = arrow.getNearbyEntities(arrowSize, arrowSize, arrowSize)
                val validNearbyPlayers =
                    nearbyEntities.asSequence().filterIsInstance<Player>()
                        .filter { it != arrow.shooter }
                        .filter { !it.isDead }.filter { it.noDamageTicks == 0 }
                        .filter { !it.hasPotionEffect(PotionEffectType.INVISIBILITY) }.toList()
                val targetPlayer =
                    validNearbyPlayers.minBy { it.location.distanceSquared(arrow.location) } ?: return
                targetPlayer.killer = player
                targetPlayer.damage(arrowDamage, arrow)
                targetPlayer.noDamageTicks = targetPlayer.maximumNoDamageTicks
                player.playSound(player.location, Sound.ENTITY_ARROW_HIT_PLAYER, 0.8F, 1F)
                arrow.remove()
                this.cancel()
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }
}
