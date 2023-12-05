package landrive.lib.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import io.vertx.core.json.jackson.DatabindCodec;

public final class ConfigLoader {
    public static <T> T load(final Class<T> configClass, final Path path, String defaultResourceName) {
        File configFile = path.toFile();
        InputStream defaultResourceStream = configClass.getClassLoader().getResourceAsStream(defaultResourceName);
        try {
            if (defaultResourceStream != null) {
                if (configFile.createNewFile()) {
                    FileOutputStream fos = new FileOutputStream(configFile);
                    fos.write(defaultResourceStream.readAllBytes());
                    fos.close();
                }
            }
            else {
                throw new IllegalArgumentException("No resource under specified name.");
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        final T config;
        try {
            config = DatabindCodec.mapper().readValue(configFile, configClass);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }
}