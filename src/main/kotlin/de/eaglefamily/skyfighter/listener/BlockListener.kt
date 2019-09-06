package de.eaglefamily.skyfighter.listener

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class BlockListener : Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) event.isCancelled = true
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.gameMode != GameMode.CREATIVE) event.isCancelled = true
    }
}
