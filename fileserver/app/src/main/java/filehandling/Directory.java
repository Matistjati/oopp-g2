package filehandling;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Directory{
    public HashMap<String, Directory> subDirectories = new HashMap<>();
    public final String path;
    public Directory(String path)
    {
        this.path = path;

        for (String dirPath : listDirectories())
        {
            subDirectories.put(dirPath, new Directory(path + "/" + dirPath));
        }
    }
    public Set<String> listDirectories()
    {
        return Stream.of(new File(path).listFiles()).filter(file -> file.isDirectory()).map(File::getName).collect(Collectors.toSet());
    }
    public Set<String> listFiles()
    {
        return Stream.of(new File(path).listFiles()).filter(file -> file.isFile()).map(File::getName).collect(Collectors.toSet());
    }
    public String toJson()
    {
        JSONObject json = new JSONObject();
        json.put("directories", new JSONArray(listDirectories()));
        json.put("files", new JSONArray(listFiles()));

        return json.toString();
    }
    public Directory getDir(Stack<String> pathStack)
    {
        Directory dir = subDirectories.get(pathStack.pop());

        return (pathStack.size() == 0) ? dir : getDir(pathStack);
    }
    public void createDirectory(String dirPath)
    {
        if (!subDirectories.containsKey(path))
        {
            subDirectories.put(dirPath, new Directory(path + "/" + dirPath));
        }
    }
    public void removeDirectory(String dirPath)
    {
        if (subDirectories.containsKey(path))
        {
            subDirectories.remove(dirPath);
        }
    }
}
