package de.eaglefamily.skyfighter.listener

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerPickupArrowEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class InventoryListener : Listener {

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) event.isCancelled = true
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked.gameMode != GameMode.CREATIVE) event.isCancelled = true
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) event.isCancelled = true
    }

    @EventHandler
    fun onPlayerAttemptPickupItem(event: PlayerAttemptPickupItemEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) {
            event.isCancelled = true
            event.flyAtPlayer = false
        }
    }

    @EventHandler
    fun onPlayerPickupArrow(event: PlayerPickupArrowEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) event.isCancelled = true
    }

    @EventHandler
    fun onPlayerPickupExperience(event: PlayerPickupExperienceEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) {
            event.isCancelled = true
            event.experienceOrb.velocity.multiply(0)
        }
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) event.isCancelled = true
    }
}
