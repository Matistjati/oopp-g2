package FileHandling;

import java.io.File;
import java.util.regex.Pattern;

public class FileHandler {
    private String folderPath;
    private FolderStructure topFolder;
    public FileHandler(String folderPath)
    {
        this.folderPath = folderPath;
        topFolder = new FolderStructure(folderPath);
    }

    public boolean exists(String path)
    {
        String[] paths = path.split("/");
        return true;
    }
}
