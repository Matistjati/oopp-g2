package landrive.lib.cli.arg;

public final class ArgumentSpec {
    public final String name;
    public final Class<?> type;

    public ArgumentSpec(final String name, final Class<?> type) {
        this.name = name;
        this.type = type;
    }
}
