package de.eaglefamily.skyfighter.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRegainHealthEvent

class RegainHealthListener : Listener {

    @EventHandler
    fun onEntityRegainHealth(event: EntityRegainHealthEvent) {
        event.isCancelled = true
    }
}
