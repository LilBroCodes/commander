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
    private final Class<? extends Enum<?>> enumClass; // Only used for ENUM

    public TypedParameter(String name, ParameterType type, @NotNull Supplier<List<String>> suggestions) {
        this(name, type, suggestions, null);
    }

    public TypedParameter(String name, ParameterType type) {
        this(name, type, type == ParameterType.BOOL ? () -> List.of("true", "false") : ArrayList::new, null);
    }

    public TypedParameter(String name, Class<? extends Enum<?>> enumClass) {
        this(name, ParameterType.ENUM,
                () -> {
                    List<String> values = new ArrayList<>();
                    for (Enum<?> constant : enumClass.getEnumConstants()) {
                        values.add(constant.name());
                    }
                    return values;
                },
                enumClass
        );
    }

    private TypedParameter(String name, ParameterType type,
                           Supplier<List<String>> suggestions,
                           Class<? extends Enum<?>> enumClass) {
        this.name = name;
        this.type = type;
        this.suggestions = suggestions;
        this.enumClass = enumClass;
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

    public Class<? extends Enum<?>> enumClass() {
        return enumClass;
    }
}
