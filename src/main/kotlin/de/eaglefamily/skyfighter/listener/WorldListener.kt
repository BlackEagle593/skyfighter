package de.eaglefamily.skyfighter.listener

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldInitEvent

class WorldListener(private val plugin: SkyfighterPlugin) : Listener {

    private val borderSize = 250.0
    private val addBorderSizePerPlayer = 20.0

    @EventHandler
    fun onWorldInit(event: WorldInitEvent) {
        val world = event.world
        if (!world.name.startsWith(plugin.map.worldName)) return

        world.keepSpawnInMemory = false
        world.isAutoSave = false

        val border = world.worldBorder
        border.setCenter(0.0, 0.0)
        border.size = borderSize + (addBorderSizePerPlayer * Bukkit.getOnlinePlayers().size)
    }
}
