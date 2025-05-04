package org.lilbrocodes.commander.api.executor;

import org.bukkit.command.CommandSender;
import java.util.List;

/**
 * Represents a node in the command execution tree.
 * This is the base class for both parameterized and parent command nodes.
 *
 * @param <T> the specific type of the ExecutorNode implementation
 */
@SuppressWarnings("unused")
public abstract class ExecutorNode<T extends ExecutorNode<T>> {
    protected final String name;
    protected final String description;
    protected final String pluginName;
    protected String permission;

    /**
     * Constructs an ExecutorNode with the given name, description, and plugin name.
     *
     * @param name the name of the node
     * @param description the description of the node
     * @param pluginName the name of the plugin that owns this node
     */
    public ExecutorNode(String name, String description, String pluginName) {
        this.name = name;
        this.description = description;
        this.pluginName = pluginName;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPluginName() { return pluginName; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }

    /**
     * Checks whether the given sender has permission to execute this node.
     * If no custom permission is set, defaults to "pluginName.commandName".
     *
     * @param sender the command sender
     * @return true if the sender has permission, false otherwise
     */
    public boolean hasPermission(CommandSender sender) {
        String fallback = pluginName + "." + name;
        return sender.hasPermission(permission != null ? permission : fallback);
    }

    /**
     * Sets a custom permission and returns this node instance for chaining.
     *
     * @param permission the permission to set
     * @return this node instance
     */
    @SuppressWarnings("unchecked")
    public T withPermission(String permission) {
        this.permission = permission;
        return (T) this;
    }

    /**
     * Executes this command node.
     *
     * @param sender the command sender
     * @param args the arguments passed to the command
     */
    public abstract void execute(CommandSender sender, List<String> args);

    /**
     * Provides tab completions for this node based on the current arguments.
     *
     * @param sender the command sender
     * @param args the current arguments
     * @return a list of suggestions
     */
    public abstract List<String> tabComplete(CommandSender sender, List<String> args);

    public abstract boolean hasSubCommands();
}
