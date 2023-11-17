package filehandling;

import org.json.JSONObject;

public abstract class JsonAbstractFile extends JSONObject {
    private String path;
    public JsonAbstractFile(String path)
    {
        this.put("path", path);
    }

    public String getPath() {
        return path;
    }

    public String getName()
    {
        String[] paths = path.split("/");
        return paths[paths.length - 1];
    }
}
