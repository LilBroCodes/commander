package org.lilbrocodes.commander.api.executor;

import org.bukkit.command.CommandSender;
import org.lilbrocodes.commander.api.argument.TypedExecutor;
import org.lilbrocodes.commander.api.util.StaticChatUtil;

import java.util.ArrayList;
import java.util.List;

/**
This executor type is a subclass of {@link CommandGroupNode}, so it can have sub-nodes, but if called without any arguments, it will run the {@link CommandHybridNode#executor} value you pass to it.
 */
@SuppressWarnings("unused")
public class CommandHybridNode extends CommandGroupNode {
    private TypedExecutor executor;

    /**
     * Constructs a PseudoExecutorNode with no children.
     *
     * @param name        the name of the node
     * @param description the description of the node
     * @param pluginName  the name of the plugin
     * @param executor    the code to run when ran without arguments
     */
    public CommandHybridNode(String name, String description, String pluginName, TypedExecutor executor) {
        super(name, description, pluginName);
        this.executor = executor;
    }

    /**
     * Constructs a PseudoExecutorNode with no children.
     *
     * @param name        the name of the node
     * @param description the description of the node
     * @param pluginName  the name of the plugin
     */
    public CommandHybridNode(String name, String description, String pluginName) {
        this(name, description, pluginName, null);
    }

    /**
     * Executes the passed {@link CommandHybridNode#executor} if arguments are empty, handles sub-commands as usual if not
     * @param sender the command sender
     * @param args the arguments passed to the command
     */
    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.isEmpty()) {
            if (executor != null) {
                executor.execute(sender, new ArrayList<>());
            } else {
                StaticChatUtil.error(sender, pluginName, String.format("Executor for command %s not set.", name));
            }
        } else {
            super.execute(sender, args);
        }
    }

    public void addExecutor(TypedExecutor executor) {
        this.executor = executor;
    }
}
