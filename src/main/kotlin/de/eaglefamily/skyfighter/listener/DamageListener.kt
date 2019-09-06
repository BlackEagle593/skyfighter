package de.eaglefamily.skyfighter.listener

import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffectType

class DamageListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.CUSTOM) event.isCancelled = true
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        event.isCancelled = true
        if (event.entity !is Player) return
        if (event.damager !is Arrow) return

        val arrow = event.damager as Arrow
        if (arrow.shooter !is Player) return

        val player = event.entity as Player
        val shooter = arrow.shooter as Player
        if (player == shooter) return

        // spawn protection
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) return

        // prevent bouncing
        arrow.velocity = player.location.subtract(arrow.location).toVector().normalize()
    }
}
