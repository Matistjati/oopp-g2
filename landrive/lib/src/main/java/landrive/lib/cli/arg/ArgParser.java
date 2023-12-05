package landrive.lib.cli.arg;

public abstract class ArgParser<T> {
    public final Class<T> type;

    protected ArgParser(final Class<T> type) {
        this.type = type;
    }

    public abstract T parse(final String token);
}
