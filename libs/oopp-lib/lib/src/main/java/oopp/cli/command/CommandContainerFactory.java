package oopp.cli.command;

import oopp.cli.argument.ArgumentSpec;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class CommandContainerFactory {

    private CommandContainerFactory() {
    }

    public static List<CommandContainer> makeCommandContainers(final List<Object> refs) {
        return refs.stream()
                .map(CommandContainerFactory::makeCommandContainers)
                .flatMap(Collection::stream)
                .toList();
    }

    public static List<CommandContainer> makeCommandContainers(final Object ref) {
        return Arrays.stream(ref.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Command.class))
                .map(method -> makeCommandContainer(ref, method))
                .toList();
    }

    private static CommandContainer makeCommandContainer(final Object ref, final Method method) {
        final List<ArgumentSpec> argumentSpecs = Arrays.stream(method.getParameters())
                .map(parameter -> new ArgumentSpec(parameter.getName(), parameter.getType()))
                .toList();

        return new CommandContainer(getCommandContainerName(method), argumentSpecs, ref, method);
    }

    private static String getCommandContainerName(final Method method) {
        final Command annotation = method.getAnnotation(Command.class);
        if (annotation.name().isEmpty()) {
            return method.getName();
        } else {
            return annotation.name();
        }
    }
}
