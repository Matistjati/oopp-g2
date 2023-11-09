package FileHandling;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


public class FolderStructure {
    private String folderName;
    private ArrayList<FolderStructure> subFolders = new ArrayList<>();
    private ArrayList<FileStructure> fileList = new ArrayList<>();

    
    public FolderStructure(String folderName)
    {
        this.folderName = folderName;
        File folder = new File(folderName);
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

    public boolean exists(String[] Paths)
    {
        if (Paths.length > 1)
        {
            for (int i = 0; i < subFolders.size(); i++)
            {
                FolderStructure targetFolder = subFolders.get(i);
                if (Paths[0] == targetFolder.getName())
                {
                    return targetFolder.exists(Arrays.copyOfRange(Paths, 1, Paths.length));
                }
            }
        }
        else
        {
            for (int i = 0; i < fileList.size(); i++)
            {
                if (fileList.get(i).getName() == Paths[0])
                {
                    return true;
                }
            }
        }
        return false;
    }
    public String getName()
    {
        return folderName;
    }
}
