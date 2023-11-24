package oopp.server;

import oopp.serialize.ByteSerialization;

import java.io.Serializable;

public class ServerInfo implements Serializable {
    private final String nameIdentifier;
    private final String hostname;
    private final int port;

    public ServerInfo(String nameIdentifier, String hostname, int port) {
        this.nameIdentifier = nameIdentifier;
        this.hostname = hostname;
        this.port = port;
    }

    public String getNameIdentifier() {
        return nameIdentifier;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public byte[] toByteArray() {
        return ByteSerialization.objectToByteArray(this);
    }

    public static ServerInfo ofByteArray(byte[] bytes) {
        return ByteSerialization.byteArrayToObject(bytes, ServerInfo.class);
    }
}
