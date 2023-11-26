package oopp.config;

import oopp.serialize.Jackson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

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
                throw new RuntimeException("no resource for default config");
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        final T config;
        try {
            config = Jackson.OBJECT_MAPPER.readValue(configFile, configClass);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }
}