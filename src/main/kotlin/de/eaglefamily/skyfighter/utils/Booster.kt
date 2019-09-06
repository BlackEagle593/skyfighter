package de.eaglefamily.skyfighter.utils

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

class Booster(private val plugin: Plugin, private val player: Player) {

    private val boostInterval = 40L
    private val maxBoosts = 5
    private val bukkitRunnable: BukkitRunnable

    val maxSpeed = 2.8
    private val speedBoost = 0.5

    init {
        bukkitRunnable = object : BukkitRunnable() {
            override fun run() {
                if (player.isOnline) {
                    addBoost()
                } else {
                    stop()
                }
            }
        }
        bukkitRunnable.runTaskTimer(plugin, 0L, boostInterval)
    }

    fun addBoost() {
        if (player.level < maxBoosts) {
            player.level++
        }
    }

    fun stop() {
        bukkitRunnable.cancel()
    }

    fun boost() {
        if (player.gameMode == GameMode.CREATIVE) return
        if (player.level <= 0) return

        if (player.isGliding) {
            // boost while flying
            player.level--
            if (player.velocity.lengthSquared() > maxSpeed) return
            var multiplier = speedBoost

            // reduce boost when flying over max height
            if (player.location.y > player.world.maxHeight) {
                multiplier *= 1 / (player.location.y - player.world.maxHeight)
            }

            player.velocity = player.velocity.add(player.location.direction.normalize().multiply(multiplier))
        } else {
            // boost into air while standing
            val level = player.level
            player.level = 0

            player.velocity = player.velocity.add(player.location.direction.normalize().multiply(speedBoost * level))
            if (!player.isOnGround) {
                player.isGliding = true
            } else {
                object : BukkitRunnable() {
                    override fun run() {
                        player.isGliding = true
                    }
                }.runTask(plugin)
            }
        }
    }
}
