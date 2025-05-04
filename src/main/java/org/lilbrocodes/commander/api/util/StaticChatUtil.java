package org.lilbrocodes.commander.api.util;

import org.bukkit.command.CommandSender;

/**
 * Utility class to send formatted chat messages with a static plugin name prefix.
 * Provides static methods for message formatting and sending.
 */
@SuppressWarnings("unused")
public class StaticChatUtil {

    /**
     * Affixes a normal message with a plugin's name as a prefix.
     *
     * @param name The name of the plugin to prefix the message.
     * @param message The message to format.
     * @return A formatted message with the plugin name prefix.
     */
    public static String affixNormal(String name, String message) {
        return String.format("§7[§r%s§7]§r %s", name, message);
    }

    /**
     * Affixes an error message with a plugin's name as a prefix.
     *
     * @param name The name of the plugin to prefix the error message.
     * @param message The error message to format.
     * @return A formatted error message with the plugin name prefix.
     */
    public static String affixError(String name, String message) {
        return affixNormal(name, String.format("§c%s", message));
    }

    /**
     * Sends an informational message to the given command sender.
     *
     * @param sender The command sender to send the message to.
     * @param name The name of the plugin to prefix the message.
     * @param message The informational message to send.
     */
    public static void info(CommandSender sender, String name, String message) {
        sender.sendMessage(affixNormal(name, message));
    }

    /**
     * Sends an error message to the given command sender.
     *
     * @param sender The command sender to send the error message to.
     * @param name The name of the plugin to prefix the error message.
     * @param message The error message to send.
     */
    public static void error(CommandSender sender, String name, String message) {
        sender.sendMessage(affixError(name, message));
    }
}

