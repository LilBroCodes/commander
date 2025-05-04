package org.lilbrocodes.commander.api.argument;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TypedArgument {
    private final String name;
    private final ArgTypes type;
    private final Supplier<List<String>> suggestions;

    /**
     * Creates a new TypedArgument.
     *
     * @param name The name of the argument.
     * @param type The type of the argument.
     * @param suggestions Optional list of suggestions for tab completion.
     */
    public TypedArgument(String name, ArgTypes type, @NotNull Supplier<List<String>> suggestions) {
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
    public TypedArgument(String name, ArgTypes type) {
        this.name = name;
        this.type = type;
        this.suggestions = ArrayList::new;
    }

    public String name() {
        return name;
    }
    public ArgTypes type() {
        return type;
    }
    public List<String> suggestions() {
        return suggestions.get();
    }
}
