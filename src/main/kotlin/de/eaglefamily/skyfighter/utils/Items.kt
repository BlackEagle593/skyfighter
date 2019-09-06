package de.eaglefamily.skyfighter.utils

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

class Items(plugin: Plugin) {

    val elytra = ItemStack(Material.ELYTRA)
    val bow = ItemStack(Material.BOW)
    val compass = ItemStack(Material.COMPASS)

    init {
        val itemMeta = elytra.itemMeta
        itemMeta.isUnbreakable = true
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        elytra.itemMeta = itemMeta

        // Update compass for every player
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach { player ->
                    val other = Bukkit.getOnlinePlayers().filter { other -> other != player }
                        .minBy { it.location.distanceSquared(player.location) }
                    val locationTarget = other?.location ?: player.world.spawnLocation
                    if (player.compassTarget != locationTarget) player.compassTarget = locationTarget
                }
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }
}
