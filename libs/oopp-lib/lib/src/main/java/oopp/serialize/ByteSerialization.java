package oopp.serialize;

import java.io.*;

public class ByteSerialization {
    public static <T extends Serializable> byte[] objectToByteArray(T object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            return bos.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Serializable> T byteArrayToObject(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            Object obj = ois.readObject();
            if (clazz.isInstance(obj)) {
                return clazz.cast(obj);
            }
            throw new RuntimeException();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
