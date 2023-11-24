package oopp.cli.command;

import oopp.cli.argument.ArgumentSpec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public final class CommandContainer {
    public final String name;
    public final List<ArgumentSpec> argumentSpecs;
    private final Object ref;
    private final Method method;

    public CommandContainer(
            final String name,
            final List<ArgumentSpec> argumentSpecs,
            final Object ref,
            final Method method
    ) {
        this.name = name;
        this.argumentSpecs = argumentSpecs;
        this.ref = ref;
        this.method = method;

        method.setAccessible(true);
    }

    public void invoke(final Object[] arguments) {
        try {
            method.invoke(ref, arguments);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
