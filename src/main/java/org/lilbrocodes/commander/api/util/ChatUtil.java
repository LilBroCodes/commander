package org.lilbrocodes.commander.api.util;

import org.bukkit.command.CommandSender;

/**
 * Utility class to send formatted chat messages with plugin name prefix.
 */
@SuppressWarnings("unused")
public class ChatUtil {
    private final String name;

    /**
     * Creates a new instance of ChatUtil.
     *
     * @param pluginName The name of the plugin, which will be used as a prefix in messages.
     */
    public ChatUtil(String pluginName) {
        this.name = pluginName;
    }

    /**
     * Affixes a normal message with the plugin's name as a prefix.
     *
     * @param message The message to format.
     * @return A formatted message with the plugin name prefix.
     */
    public String affixNormal(String message) {
        return String.format("§7[§r%s§7]§r %s", this.name, message);
    }

    /**
     * Affixes an error message with the plugin's name as a prefix.
     *
     * @param message The error message to format.
     * @return A formatted error message with the plugin name prefix.
     */
    public String affixError(String message) {
        return affixNormal(String.format("§c%s", message));
    }

    /**
     * Sends an informational message to the given command sender.
     *
     * @param sender The command sender to send the message to.
     * @param message The informational message to send.
     */
    public void info(CommandSender sender, String message) {
        sender.sendMessage(affixNormal(message));
    }

    /**
     * Sends an error message to the given command sender.
     *
     * @param sender The command sender to send the error message to.
     * @param message The error message to send.
     */
    public void error(CommandSender sender, String message) {
        sender.sendMessage(affixError(message));
    }
}
