package de.eaglefamily.skyfighter.utils

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.text.MessageFormat
import java.util.*

class Messages(plugin: Plugin) {

    private val bundle: ResourceBundle = ResourceBundle.getBundle(plugin.name)

    fun getString(key: String, vararg arguments: Any): String {
        return MessageFormat(bundle.getString(key)).format(arguments)
    }

    fun sendMessage(sender: CommandSender, key: String, vararg arguments: Any) {
        sender.sendMessage(getString(key, *arguments))
    }

    fun sendMessage(player: Player, key: String, vararg arguments: Any) {
        player.sendMessage(getString(key, *arguments))
    }

    fun sendMessageToAll(key: String, vararg arguments: Any) {
        Bukkit.getOnlinePlayers().forEach { sendMessage(it, key, *arguments) }
    }
}
