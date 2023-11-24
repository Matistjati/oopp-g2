package oopp.cli.command;

import oopp.cli.argument.ArgParser;
import oopp.cli.argument.ArgParsers;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import static java.util.function.Function.identity;

public final class CommandDispatcher {
    private final Map<String, CommandContainer> commandContainers;
    private final Map<Class<?>, ArgParser<?>> argParsers;

    public CommandDispatcher(
            final Map<String, CommandContainer> commandContainers,
            final Map<Class<?>, ArgParser<?>> argParsers
    ) {
        this.commandContainers = commandContainers;
        this.argParsers = argParsers;
    }

    public CommandDispatcher(
            final List<CommandContainer> commandContainers,
            final List<ArgParser<?>> argParsers
    ) {
        this.commandContainers = commandContainers.stream()
                .collect(Collectors.toMap(commandContainer -> commandContainer.name, identity()));

        this.argParsers = argParsers.stream()
                .collect(Collectors.toMap(argParser -> argParser.type, identity()));
    }

    public CommandDispatcher(final List<CommandContainer> commandContainers) {
        this(commandContainers, ArgParsers.DEFAULT_ARG_PARSERS);
    }

    public void dispatch(final String input) {
        final List<String> exploded = Arrays.stream(input.split(" ")).collect(Collectors.toList());

        final String commandName = exploded.remove(0);
        final CommandContainer commandContainer = commandContainers.get(commandName);
        if (commandContainer == null) {
            // FIXME: Exception
            return;
        }

        final Object[] arguments = commandContainer.argumentSpecs.stream()
                .map(parameter -> argParsers.get(parameter.type))
                .map(argParser -> argParser.parse(exploded.remove(0)))
                .toArray();

        commandContainer.invoke(arguments);
    }
}
