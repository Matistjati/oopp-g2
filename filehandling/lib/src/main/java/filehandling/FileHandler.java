package filehandling;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
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
        int startPosition = 0;
        String[] topFolderPaths = topFolder.getName().split("/");

        if (paths.length >= 2 && paths[0].equals(topFolderPaths[0]) && paths[1].equals(topFolderPaths[1]))
        {
            startPosition = 2;
        }


        for (int i = startPosition; i < paths.length; i++)
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
        FolderStructure folder = topFolder;
        if (paths.length > 1) {
            String[] folderPaths = new String[paths.length - 1];

            System.arraycopy(paths, 0, folderPaths, 0, paths.length-1);
            folder = getFolder(folderPaths);
        }
        return folder.getFile(path);
    }

    public void deleteFile(String path)
    {
        FileStructure targetFile = getFile(path);
        targetFile.delete();
    }
    public void createFile(String folderPath, String fileName)
    {
        FolderStructure targetFolder = getFolder(folderPath);
        targetFolder.createFile(fileName);
    }

    public void writeFile(String path, String data)
    {
        FileStructure targetFile = getFile(path);
        targetFile.write(data);
    }

    public String readData(String path)
    {
        return getFile(path).read();
    }

    public FileInputStream getFileInputStream(String path)
    {
        return  getFile(path).getFileInputStream();
    }

    public void createFolder(String folderPath, String foldername)
    {
        FolderStructure targetFolder = getFolder(folderPath);
        targetFolder.createFolder(foldername);
    }

}
