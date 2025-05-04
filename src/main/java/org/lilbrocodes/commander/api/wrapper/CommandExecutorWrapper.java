package org.lilbrocodes.commander.api.wrapper;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.lilbrocodes.commander.api.argument.TypedArgument;
import org.lilbrocodes.commander.api.executor.ExecutorNode;
import org.lilbrocodes.commander.api.executor.ParameterExecutorNode;
import org.lilbrocodes.commander.api.executor.ParentExecutorNode;

import java.util.Arrays;

/**
 * Wrapper class for handling command execution with complex command structures, subcommands, permissions, and help messages.
 */
@SuppressWarnings("unused")
public class CommandExecutorWrapper implements CommandExecutor {
    private final ExecutorNode<ParentExecutorNode> root;

    /**
     * Creates a new CommandExecutorWrapper.
     *
     * @param root The root executor node, representing the root of the command tree.
     */
    public CommandExecutorWrapper(ExecutorNode<ParentExecutorNode> root) {
        this.root = root;
    }

    /**
     * Handles a command execution by validating permissions, checking for subcommands, and executing the correct command.
     *
     * @param sender The command sender executing the command.
     * @param cmd The command being executed.
     * @param label The label used to execute the command.
     * @param args The arguments passed with the command.
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!root.hasPermission(sender)) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("help") && root instanceof ParentExecutorNode parent && !hasHelpSubcommand(parent)) {
            if (args.length >= 2 && args[1].equalsIgnoreCase("tree")) {
                sender.sendMessage("§eCommand Tree:");
                printTree(sender, parent, label, "", true);
                return true;
            }

            sender.sendMessage("§eAvailable subcommands:");
            for (ExecutorNode<?> child : parent.getChildren()) {
                if (!child.hasPermission(sender)) continue;

                StringBuilder line = new StringBuilder("§7- /" + label + " " + child.getName());
                if (child instanceof ParameterExecutorNode paramNode) {
                    for (TypedArgument arg : paramNode.getArguments()) {
                        line.append(" §8<").append(arg.name()).append(">§7");
                    }
                }
                line.append(" §f- ").append(child.getDescription());
                sender.sendMessage(line.toString());
            }
            sender.sendMessage(String.format("Use '§7/%s help tree§r' for more details.", parent.getName()));
            return true;
        }

        root.execute(sender, Arrays.asList(args));
        return true;
    }

    /**
     * Prints the command tree recursively to show subcommands and their descriptions.
     *
     * @param sender The command sender to send the tree to.
     * @param node The current node being processed in the command tree.
     * @param path The command path to this node.
     * @param prefix The prefix used for formatting the tree.
     * @param isLast Indicates whether this is the last child in the tree.
     */
    private void printTree(CommandSender sender, ParentExecutorNode node, String path, String prefix, boolean isLast) {
        if (!node.hasPermission(sender)) return;

        String branch = prefix + (isLast ? "└─ " : "├─ ");
        sender.sendMessage(branch + "§a" + path + " §7- " + node.getDescription());

        int i = 0;
        var visibleChildren = node.getChildren().stream().filter(c -> c.hasPermission(sender)).toList();

        for (ExecutorNode<?> child : visibleChildren) {
            boolean lastChild = (visibleChildren.size() - 1) == i;

            if (child instanceof ParentExecutorNode parentChild) {
                printTree(sender, parentChild, path + " " + child.getName(), prefix + (isLast ? "   " : "│  "), lastChild);
            } else if (child instanceof ParameterExecutorNode paramChild) {
                StringBuilder params = new StringBuilder();
                for (TypedArgument arg : paramChild.getArguments()) {
                    params.append("<").append(arg.name()).append(":").append(arg.type().name().toLowerCase()).append(">");
                }
                String childLine = prefix + (isLast ? "   " : "│  ") + (lastChild ? "└─ " : "├─ ") +
                        "§b" + path + " " + child.getName() +
                        (!params.isEmpty() ? " §8" + params : "") +
                        " §7- " + paramChild.getDescription();
                sender.sendMessage(childLine);
            }

            i++;
        }
    }

    private boolean hasHelpSubcommand(ParentExecutorNode node) {
        for (ExecutorNode<?> subNode : node.getChildren()) {
            if (subNode.getName().equals("help")) return true;
            else if (subNode instanceof ParentExecutorNode parentNode && hasHelpSubcommand(parentNode)) return true;
        }
        return false;
    }
}

