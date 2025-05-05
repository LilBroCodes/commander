package org.lilbrocodes.commander.api;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.lilbrocodes.commander.api.executor.CommandGroupNode;
import org.lilbrocodes.commander.api.wrapper.CommandExecutorWrapper;
import org.lilbrocodes.commander.api.executor.ExecutorNode;
import org.lilbrocodes.commander.api.wrapper.TabCompleterWrapper;

/**
 * This class represents a command that can be registered in a Minecraft plugin
 * using the Commander library. It handles the registration of the command
 * executor and, optionally, the tab completer for the specified command.
 * <p>
 * The root node of the command is provided along with an option to enable or
 * disable tab completion.
 */
@SuppressWarnings("unused")
public class CommanderCommand {

    private ExecutorNode<CommandGroupNode> root;
    private final boolean tabComplete;

    /**
     * Constructs a new {@link CommanderCommand} with the specified root node
     * and tab completion flag.
     *
     * @param root        The root executor node, which defines the command structure.
     * @param tabComplete A flag to specify whether tab completion should be enabled.
     */
    public CommanderCommand(ExecutorNode<CommandGroupNode> root, boolean tabComplete) {
        this.root = root;
        this.tabComplete = tabComplete;
    }

    /**
     * Registers this command with the specified plugin and command name.
     * Sets the executor and optionally the tab completer for the command.
     *
     * @param plugin      The plugin to which the command is to be registered.
     * @param commandName The name of the command to be registered.
     * @return {@code true} if the command was successfully registered, {@code false}
     *         if the command does not exist in the plugin.
     */
    public boolean register(JavaPlugin plugin, String commandName) {
        initialize(root);

        PluginCommand command = plugin.getCommand(commandName);
        if (command == null) return false;

        // Set the command executor
        command.setExecutor(new CommandExecutorWrapper(root));

        // Optionally set the tab completer
        if (tabComplete) {
            command.setTabCompleter(new TabCompleterWrapper(root));
        }

        return true;
    }

    /**
     * A method that is called in {@code register} that you can override to
     * set up the command
     *
     * @param root  The root object of the command.
     */
    public void initialize(ExecutorNode<CommandGroupNode> root) {
        this.root = root;
    }
}
