import java.util.function.Function;

public class ArgsParser {
    public static <T> T parse(String[] args, String arg, Function<String, T> parseFun, T defaultValue) {
        T parsed = parse(args, arg, parseFun);
        return parsed == null ? defaultValue : parsed;
    }

    public static <T> T parse(String[] args, String arg, Function<String, T> parseFun) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(arg) && i + 1 < args.length) {
                return parseFun.apply(args[i + 1]);
            }
        }
        return null;
    }
}
