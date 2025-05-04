package org.lilbrocodes.commander.api.argument;

import org.bukkit.command.CommandSender;
import java.util.List;

@FunctionalInterface
public interface TypedExecutor {
    void execute(CommandSender sender, List<Object> parsedArgs);
}
