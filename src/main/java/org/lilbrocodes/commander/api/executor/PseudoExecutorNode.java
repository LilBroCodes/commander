package org.lilbrocodes.commander.api.executor;

import org.bukkit.command.CommandSender;
import org.lilbrocodes.commander.api.argument.TypedExecutor;

import java.util.ArrayList;
import java.util.List;

/**
This executor type is a subclass of {@link ParentExecutorNode}, so it can have sub-nodes, but if called without any arguments, it will run the {@link PseudoExecutorNode#executor} value you pass to it.
 */
@SuppressWarnings("unused")
public class PseudoExecutorNode extends ParentExecutorNode {
    private final TypedExecutor executor;

    /**
     * Constructs a PseudoExecutorNode with no children.
     *
     * @param name        the name of the node
     * @param description the description of the node
     * @param pluginName  the name of the plugin
     */
    public PseudoExecutorNode(String name, String description, String pluginName, TypedExecutor executor) {
        super(name, description, pluginName);
        this.executor = executor;
    }

    /**
     * Executes the passed {@link PseudoExecutorNode#executor} if arguments are empty, handles sub-commands as usual if not
     * @param sender the command sender
     * @param args the arguments passed to the command
     */
    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.isEmpty()) {
            executor.execute(sender, new ArrayList<>());
        } else {
            super.execute(sender, args);
        }
    }
}
