package de.eaglefamily.skyfighter.listener

import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent

class EnvironmentListener : Listener {

    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        if (event.entity is Arrow && (event.entity as Arrow).shooter is Player) return
        event.isCancelled = true
    }
}
