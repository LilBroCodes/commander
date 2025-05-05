package org.lilbrocodes.commander.api.wrapper;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.lilbrocodes.commander.api.executor.ExecutorNode;
import org.lilbrocodes.commander.api.executor.CommandGroupNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Wrapper class for handling tab completion of commands based on the command tree structure.
 */
@SuppressWarnings("unused")
public class TabCompleterWrapper implements TabCompleter {
    private final ExecutorNode<CommandGroupNode> root;

    /**
     * Creates a new TabCompleterWrapper.
     *
     * @param root The root executor node, representing the root of the command tree.
     */
    public TabCompleterWrapper(ExecutorNode<CommandGroupNode> root) {
        this.root = root;
    }

    /**
     * Handles tab completion for a command based on the current input and the command tree structure.
     *
     * @param sender The command sender requesting tab completion.
     * @param cmd The command being completed.
     * @param alias The alias used to execute the command.
     * @param args The current arguments being typed.
     * @return A list of possible completions based on the command tree.
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        return Stream.concat(
                root.tabComplete(sender, Arrays.asList(args)).stream(),
                args.length == 1 ?
                        Stream.of("help") :
                        args.length >= 1 && args[0].equals("help") ?
                                Stream.of("tree") :
                                Stream.empty()
        ).toList();
    }
}