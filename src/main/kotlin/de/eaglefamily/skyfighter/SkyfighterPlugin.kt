package de.eaglefamily.skyfighter

import de.eaglefamily.skyfighter.listener.*
import de.eaglefamily.skyfighter.utils.Items
import de.eaglefamily.skyfighter.utils.Messages
import de.eaglefamily.skyfighter.utils.SkyfighterMap
import de.eaglefamily.skyfighter.utils.Spawnpoint
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class SkyfighterPlugin : JavaPlugin() {

    val wrappedPlayers = mutableMapOf<Player, WrappedPlayer>()
    val messages = Messages(this)
    lateinit var items: Items
    lateinit var map: SkyfighterMap
    lateinit var spawnpoint: Spawnpoint

    private val mapChangeDelay = 20L * 60 * 15 // 15 min

    override fun onEnable() {
        items = Items(this)
        map = SkyfighterMap()
        spawnpoint = Spawnpoint(this)

        registerListener()

        map.generateNewMap()
        loadPlayers()

        object : BukkitRunnable() {
            override fun run() {
                map.generateNewMap()
                Bukkit.getOnlinePlayers().forEach {
                    val randomSpawnpoint = spawnpoint.randomSpawnpoint()
                    it.teleport(randomSpawnpoint)
                }
            }
        }.runTaskTimer(this, mapChangeDelay, mapChangeDelay)
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().forEach { it.kickPlayer("") }
        map.unload()
    }

    private fun registerListener() {
        val pluginManager = server.pluginManager
        pluginManager.registerEvents(BlockListener(), this)
        pluginManager.registerEvents(DamageListener(this), this)
        pluginManager.registerEvents(EnvironmentListener(), this)
        pluginManager.registerEvents(FoodLevelListener(), this)
        pluginManager.registerEvents(GlideListener(this), this)
        pluginManager.registerEvents(InteractListener(this), this)
        pluginManager.registerEvents(InventoryListener(), this)
        pluginManager.registerEvents(MoveListener(this), this)
        pluginManager.registerEvents(PlayerDeathListener(this), this)
        pluginManager.registerEvents(PlayerJoinListener(this), this)
        pluginManager.registerEvents(PlayerQuitListener(this), this)
        pluginManager.registerEvents(PlayerRespawnListener(this), this)
        pluginManager.registerEvents(RegainHealthListener(), this)
        pluginManager.registerEvents(SneakListener(this), this)
        pluginManager.registerEvents(WorldListener(this), this)
    }

    private fun loadPlayers() {
        Bukkit.getOnlinePlayers().stream().forEach { player -> wrappedPlayers[player] = WrappedPlayer(this, player) }
    }
}
