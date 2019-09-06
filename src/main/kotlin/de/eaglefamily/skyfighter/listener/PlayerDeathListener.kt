package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.min

class PlayerDeathListener(private val plugin: SkyfighterPlugin) :
    Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity

        event.deathMessage = null

        if (player.killer != null) {
            player.killer!!.health = min(
                player.killer!!.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value, player.killer!!.health + 6.0
            )
            plugin.wrappedPlayers[player]?.addKiller(player.killer!!)
            val wrappedKiller = plugin.wrappedPlayers[player.killer!!]
            wrappedKiller?.removeKiller(player)
            plugin.messages.sendMessageToAll("kill", player.displayName, player.killer!!.displayName)
            wrappedKiller?.addKill()
        } else {
            plugin.messages.sendMessageToAll("death", player.displayName)
        }

        event.keepInventory = true
        event.drops.clear()
        event.keepLevel = true
        event.droppedExp = 0
        player.level = 0
        player.exp = 0F

        object : BukkitRunnable() {
            override fun run() {
                player.spigot().respawn()
            }
        }.runTask(plugin)
    }
}
