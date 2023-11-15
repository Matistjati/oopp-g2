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

    public FolderStructure getFolder(String[] paths)
    {
        FolderStructure targetStructure = topFolder;
        for (int i = 0; i < paths.length; i++)
        {
            targetStructure = targetStructure.getFolder(paths[i]);
        }

        return  targetStructure;
    }

    public FolderStructure getFolder(String path)
    {
        return getFolder(path.split("/"));
    }

    public FileStructure getFile(String path)
    {
        String[] paths = path.split("/");
        String [] folderPaths = new String[paths.length - 1];

        System.arraycopy(paths, 1, folderPaths, 0, paths.length);
        FolderStructure folder = getFolder(folderPaths);
        return folder.getFile(paths[paths.length - 1]);
    }
}
