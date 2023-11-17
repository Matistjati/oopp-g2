package filehandling;

import org.json.JSONObject;

public abstract class JsonAbstractFile extends JSONObject {
    final static String pathKey = "path";
    public JsonAbstractFile(String path)
    {
        this.put(pathKey, path);
    }

    public String getPath() {
        return this.getString(pathKey);
    }

    public String getName()
    {
        String[] paths = this.getString(pathKey).split("/");
        return paths[paths.length - 1];
    }
}
