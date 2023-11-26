package oopp.cli.arg;

import oopp.cli.arg.impl.ProxyArgParser;

import java.util.List;

import static java.util.function.Function.identity;

public final class ArgParsers {
    public static final List<ArgParser<?>> DEFAULT_ARG_PARSERS = List.of(
            new ProxyArgParser<>(String.class, identity()),
            new ProxyArgParser<>(Integer.class, Integer::parseInt),
            new ProxyArgParser<>(int.class, Integer::parseInt)
    );

    private ArgParsers() {
    }
}
