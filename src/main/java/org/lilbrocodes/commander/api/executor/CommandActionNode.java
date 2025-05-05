package org.lilbrocodes.commander.api.executor;

import org.bukkit.command.CommandSender;
import org.lilbrocodes.commander.api.argument.ParameterType;
import org.lilbrocodes.commander.api.argument.TypedParameter;
import org.lilbrocodes.commander.api.argument.TypedExecutor;
import org.lilbrocodes.commander.api.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A command node that executes logic based on expected typed parameters.
 */
@SuppressWarnings("unused")
public class CommandActionNode extends ExecutorNode<CommandActionNode> {
    private final List<TypedParameter> expectedArgs;
    private final TypedExecutor executor;

    /**
     * Constructs a ParameterExecutorNode with the expected argument types and execution logic.
     *
     * @param name the name of the node
     * @param description the description of the node
     * @param pluginName the name of the owning plugin
     * @param expectedArgs the list of expected typed arguments
     * @param executor the executor that handles logic for this node
     */
    public CommandActionNode(String name, String description, String pluginName,
                             List<TypedParameter> expectedArgs,
                             TypedExecutor executor) {
        super(name, description, pluginName);
        this.expectedArgs = expectedArgs;
        this.executor = executor;
    }

    /**
     * Executes the command by parsing and validating typed parameters.
     *
     * @param sender the command sender
     * @param args the provided command arguments
     */
    @Override
    public void execute(CommandSender sender, List<String> args) {
        ChatUtil chat = new ChatUtil(this.pluginName);
        List<Object> parsed = new ArrayList<>();
        int argIndex = 0;

        for (TypedParameter expectedArg : expectedArgs) {
            if (argIndex >= args.size()) {
                chat.error(sender, "Missing parameter: " + expectedArg.name());
                return;
            }

            String raw = args.get(argIndex);
            ParameterType type = expectedArg.type();

            try {
                switch (type) {
                    case STRING -> {
                        parsed.add(raw);
                        argIndex++;
                    }
                    case QUOTED_STRING -> {
                        if (!raw.startsWith("\"")) {
                            chat.error(sender, "Quoted string must start with a quote (\").");
                            return;
                        }

                        StringBuilder quoted = new StringBuilder(raw);
                        while (!raw.endsWith("\"") && argIndex + 1 < args.size()) {
                            argIndex++;
                            raw = args.get(argIndex);
                            quoted.append(" ").append(raw);
                        }

                        if (!quoted.toString().endsWith("\"")) {
                            chat.error(sender, "Quoted string must end with a quote (\").");
                            return;
                        }

                        if (raw.equals("\"")) {
                            chat.error(sender, "Quoted string missing content and end quote.");
                            return;
                        }

                        parsed.add(quoted.substring(1, quoted.length() - 1));
                        argIndex++;
                    }
                    case GREEDY_STRING -> {
                        StringBuilder greedy = new StringBuilder(args.get(argIndex));
                        for (int j = argIndex + 1; j < args.size(); j++) {
                            greedy.append(" ").append(args.get(j));
                        }
                        parsed.add(greedy.toString());
                        argIndex = args.size();
                    }
                    case SHORT -> {
                        parsed.add(Short.parseShort(raw));
                        argIndex++;
                    }
                    case INT -> {
                        parsed.add(Integer.parseInt(raw));
                        argIndex++;
                    }
                    case LONG -> {
                        parsed.add(Long.parseLong(raw));
                        argIndex++;
                    }
                }
            } catch (NumberFormatException e) {
                chat.error(sender, "Invalid number for parameter '" + expectedArg.name() + "': " + raw);
                return;
            }
        }

        executor.execute(sender, parsed);
    }

    /**
     * Handles tab completion for the parameters of this node.
     *
     * @param sender The command sender requesting tab completion.
     * @param currentArgs The current arguments being typed.
     * @return A list of possible completions based on the argument list.
     */
    @Override
    public List<String> tabComplete(CommandSender sender, List<String> currentArgs) {
        List<String> completions = new ArrayList<>();

        int argIndex = currentArgs.size() - 1;
        if (argIndex >= expectedArgs.size()) {
            return completions;
        }

        TypedParameter currentArg = expectedArgs.get(argIndex);

        if (!currentArg.suggestions().isEmpty()) {
            for (String suggestion : currentArg.suggestions()) {
                if (suggestion.toLowerCase().startsWith(currentArgs.get(argIndex).toLowerCase())) {
                    completions.add(suggestion);
                }
            }
        }

        return completions;
    }

    public List<TypedParameter> getArguments() {
        return expectedArgs;
    }

    @Override
    public boolean hasSubCommands() {
        return false;
    }
}
