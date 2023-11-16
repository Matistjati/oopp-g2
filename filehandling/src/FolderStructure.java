package src;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FolderStructure {
    private String folderName;
    private ArrayList<FolderStructure> subFolders = new ArrayList<>();
    private ArrayList<FileStructure> fileList = new ArrayList<>();

    
    public FolderStructure(String folderName)
    {
        this.folderName = folderName;
        File folder = new File(folderName);
        if (!(folder.exists()))
        {
            folder.mkdir();
        }
        String[] files = folder.list();
        for (int i = 0; i < files.length; i++)
        {
            String newPath = folderName + "/" + files[i];
            if(Files.isDirectory(Paths.get(newPath)))
            {
                subFolders.add(new FolderStructure(newPath));
            }
            else
            {
                fileList.add(new FileStructure(newPath));
            }
        }
    }

    public FolderStructure getFolder(String path)
    {
        for (int i = 0; i < subFolders.size(); i++)
        {
            FolderStructure targetStructure = subFolders.get(i);
            if(targetStructure.getName() == path)
            {
                return targetStructure;
            }
        }
        throw new IllegalArgumentException("Failed to find folder: " + path + " in folder: " + folderName);
    }
    public FileStructure getFile(String path)
    {
        for (int i = 0; i < fileList.size(); i++)
        {
            FileStructure targetStructure = fileList.get(i);
            if(targetStructure.getName().equals(path))
            {
                return targetStructure;
            }
        }
        throw new IllegalArgumentException("Failed to find file: " + path + " in folder: " + folderName);
    }

    public void createFile(String name)
    {
        FileStructure newFile = new FileStructure(folderName + "/" + name);
        fileList.add(newFile);
    }

    public void createFolder(String name)
    {
        subFolders.add(new FolderStructure(folderName + "/" + name));
    }
    public String getName()
    {
        return folderName;
    }


}
