package org.lilbrocodes.commander.api.executor;

import org.bukkit.command.CommandSender;
import org.lilbrocodes.commander.api.util.ChatUtil;

import java.util.*;

/**
 * A command node that can have and dispatch to child subcommands.
 */
@SuppressWarnings("unused")
public class CommandGroupNode extends ExecutorNode<CommandGroupNode> {
    private final Map<String, ExecutorNode<?>> children = new HashMap<>();

    /**
     * Constructs a ParentExecutorNode with no children.
     *
     * @param name the name of the node
     * @param description the description of the node
     * @param pluginName the name of the plugin
     */
    public CommandGroupNode(String name, String description, String pluginName) {
        super(name, description, pluginName);
    }

    /**
     * Adds a subcommand to this node.
     *
     * @param node the child ExecutorNode to add
     */
    public void addChild(ExecutorNode<?> node) {
        children.put(node.getName(), node);
    }

    /**
     * Adds multiple subcommands to this node.
     *
     * @param nodes the child ExecutorNodes to add
     */
    public final void addChildren(ExecutorNode<?>... nodes) {
        for (ExecutorNode<?> node : nodes) {
            addChild(node);
        }
    }


    /**
     * Executes a subcommand based on the first argument.
     *
     * @param sender the command sender
     * @param args the arguments passed to the command
     */
    @Override
    public void execute(CommandSender sender, List<String> args) {
        ChatUtil chat = new ChatUtil(this.pluginName);

        if (args.isEmpty()) {
            chat.error(sender, "Missing subcommand. Try /" + name + " help");
            return;
        }

        ExecutorNode<?> child = children.get(args.get(0));
        if (child == null) {
            chat.error(sender, "Unknown subcommand: " + args.get(0));
            return;
        }

        child.execute(sender, args.subList(1, args.size()));
    }

    /**
     * Returns tab completions for subcommands.
     *
     * @param sender the command sender
     * @param args the current arguments
     * @return a list of tab-completion suggestions
     */
    @Override
    public List<String> tabComplete(CommandSender sender, List<String> args) {
        if (args.size() == 1) {
            return children.keySet().stream().filter(k -> k.startsWith(args.get(0))).toList();
        }

        ExecutorNode<?> child = children.get(args.get(0));
        if (child == null) return Collections.emptyList();
        return child.tabComplete(sender, args.subList(1, args.size()));
    }

    @Override
    public boolean hasSubCommands() {
        return true;
    }

    public Collection<ExecutorNode<?>> getChildren() {
        return children.values();
    }
}
