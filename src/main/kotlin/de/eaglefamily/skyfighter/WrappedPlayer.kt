package de.eaglefamily.skyfighter

import de.eaglefamily.skyfighter.utils.Booster
import de.eaglefamily.skyfighter.utils.Weapon
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class WrappedPlayer(private val plugin: SkyfighterPlugin, private val player: Player) {

    val booster = Booster(plugin, player)
    val weapon = Weapon(plugin, player)

    private val spawnProtectionDuration = 20L * 5
    private val lastKillerTeamName = "LastKiller"

    private var killstreak = 0

    init {
        player.gameMode = GameMode.ADVENTURE
        player.inventory.clear()
        player.inventory.chestplate = plugin.items.elytra
        player.inventory.setItem(0, plugin.items.bow)
        player.inventory.setItem(8, plugin.items.compass)

        player.canPickupItems = false
        player.maximumNoDamageTicks = 0
        player.health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
        player.remainingAir = player.maximumAir
        player.level = 0
        player.exp = 0F

        // Initialize scoreboard teams
        player.scoreboard = Bukkit.getServer().scoreboardManager.newScoreboard
        val lastKillerTeam =
            player.scoreboard.getTeam(lastKillerTeamName) ?: player.scoreboard.registerNewTeam(lastKillerTeamName)
        lastKillerTeam.color = ChatColor.RED
        val randomSpawnpoint = plugin.spawnpoint.randomSpawnpoint()
        player.teleport(randomSpawnpoint)

        reset()
    }

    fun reset() {
        killstreak = 0
        player.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, Int.MAX_VALUE, 0, true, false, false))
        player.noDamageTicks = player.maximumNoDamageTicks
        player.killer = null
        player.velocity = Vector(0, 0, 0)

        // spawn protection
        player.allowFlight = true
        object : BukkitRunnable() {
            override fun run() {
                player.isFlying = true
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.INVISIBILITY,
                        Int.MAX_VALUE,
                        0,
                        false,
                        false,
                        false
                    )
                )
            }
        }.runTask(plugin)

        object : BukkitRunnable() {
            override fun run() {
                if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY)) return
                player.removePotionEffect(PotionEffectType.INVISIBILITY)
                player.isFlying = false
                player.allowFlight = false
                player.isGliding = true
            }
        }.runTaskLater(plugin, spawnProtectionDuration)
    }

    fun addKiller(killer: Player) {
        player.scoreboard.getTeam(lastKillerTeamName)?.addEntry(killer.displayName)
    }

    fun removeKiller(killer: Player) {
        player.scoreboard.getTeam(lastKillerTeamName)?.removeEntry(killer.displayName)
    }

    fun addKill() {
        killstreak++
        if (killstreak % 5 == 0) {
            plugin.messages.sendMessageToAll("killstreak", player.displayName, killstreak)
        }
    }
}
