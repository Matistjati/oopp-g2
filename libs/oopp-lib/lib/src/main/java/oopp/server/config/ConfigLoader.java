package oopp.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import oopp.serialize.InetSocketAddressDeserializer;
import oopp.serialize.InetSocketAddressSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Path;

public final class ConfigLoader {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(InetSocketAddress.class, new InetSocketAddressSerializer());
        module.addDeserializer(InetSocketAddress.class, new InetSocketAddressDeserializer());
        OBJECT_MAPPER.registerModule(module);
    }

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
            config = OBJECT_MAPPER.readValue(configFile, configClass);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }
}