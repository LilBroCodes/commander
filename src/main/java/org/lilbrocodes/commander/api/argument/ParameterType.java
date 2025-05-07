package org.lilbrocodes.commander.api.argument;

@SuppressWarnings("unused")
public enum ParameterType {
    STRING,             // No quotes needed, spaces count as end of argument
    QUOTED_STRING,      // Quotes around it define argument start/end, spaces are counted as characters
    GREEDY_STRING,      // No quotes needed, EVERY character after it's start is counted as this argument
    SHORT,              // Short
    INT,                // Integer
    LONG,               // Long
    BOOL,               // Boolean
    DOUBLE,             // Double
    FLOAT,              // Float,
    ENUM                // Enum
}
