package de.eaglefamily.skyfighter.utils

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import java.io.File
import java.util.*

class SkyfighterMap {

    val worldName = "Skyfighter-"
    lateinit var world: World

    fun generateNewMap() {
        var lastWorld: World? = null
        if (::world.isInitialized) lastWorld = world

        // create new world
        val worldCreator = WorldCreator(worldName + UUID.randomUUID().toString())
        worldCreator.type(WorldType.AMPLIFIED)
        world = worldCreator.createWorld()!!

        if (lastWorld != null) delete(lastWorld)
    }

    fun unload() {
        if (!::world.isInitialized) return
        Bukkit.getOnlinePlayers().forEach { it.teleport(Bukkit.getWorlds()[0].spawnLocation) }
        delete(world)
    }

    private fun delete(world: World) {
        Bukkit.unloadWorld(world, false)
        val lastWorldDirectory = File(Bukkit.getWorldContainer(), world.name)
        deleteDirectory(lastWorldDirectory)
    }

    private fun deleteDirectory(directory: File) {
        directory.listFiles()?.forEach { deleteDirectory(it) }
        directory.delete()
    }
}
