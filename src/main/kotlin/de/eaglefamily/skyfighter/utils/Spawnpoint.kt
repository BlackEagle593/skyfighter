package de.eaglefamily.skyfighter.utils

import de.eaglefamily.skyfighter.SkyfighterPlugin
import org.bukkit.Location
import java.util.*
import kotlin.math.atan2

class Spawnpoint(private val plugin: SkyfighterPlugin) {

    private val random = Random()
    private val borderPercentageOffset = 0.1
    private val minHeight = 120
    private val aboveGround = 50

    fun randomSpawnpoint(): Location {
        val world = plugin.map.world
        val worldSize = world.worldBorder.size
        val range = (worldSize * (1 - borderPercentageOffset)).toInt()
        val x = random.nextInt(range) - (range / 2)
        val z = random.nextInt(range) - (range / 2)
        var y = world.maxHeight
        while (Location(world, x.toDouble(), y.toDouble(), z.toDouble()).block.isEmpty && y > minHeight) {
            y--
        }

        val yaw = (Math.toDegrees(atan2(z.toDouble(), x.toDouble()))).toFloat() + 90

        return Location(world, x.toDouble(), y.toDouble() + aboveGround, z.toDouble(), yaw, 0F)
    }
}
