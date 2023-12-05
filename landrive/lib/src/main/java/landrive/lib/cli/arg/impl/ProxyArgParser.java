package landrive.lib.cli.arg.impl;

import landrive.lib.cli.arg.ArgParser;

import java.util.function.Function;

public final class ProxyArgParser<T> extends ArgParser<T> {
    private final Function<String, T> map;

    public ProxyArgParser(final Class<T> type, final Function<String, T> map) {
        super(type);
        this.map = map;
    }

    @Override
    public T parse(final String token) {
        try {
            return map.apply(token);
        } catch (final Throwable cause) {
            throw new RuntimeException(cause);
        }
    }
}
