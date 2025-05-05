package org.lilbrocodes.commander.api.argument;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TypedParameter {
    private final String name;
    private final ParameterType type;
    private final Supplier<List<String>> suggestions;

    /**
     * Creates a new TypedArgument.
     *
     * @param name The name of the argument.
     * @param type The type of the argument.
     * @param suggestions Optional list of suggestions for tab completion.
     */
    public TypedParameter(String name, ParameterType type, @NotNull Supplier<List<String>> suggestions) {
        this.name = name;
        this.type = type;
        this.suggestions = suggestions;
    }

    /**
     * Creates a new TypedArgument.
     *
     * @param name The name of the argument.
     * @param type The type of the argument.
     */
    public TypedParameter(String name, ParameterType type) {
        this.name = name;
        this.type = type;
        if (type == ParameterType.BOOL) {
            this.suggestions = () -> List.of("true", "false");
        } else {
            this.suggestions = ArrayList::new;
        }
    }

    public String name() {
        return name;
    }
    public ParameterType type() {
        return type;
    }
    public List<String> suggestions() {
        return suggestions.get();
    }
}
