package filehandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {
    private String folderPath;
    private FolderStructure topFolder;
    final String metadataSavePath = "./metadata.json";
    public FileHandler(String folderPath)
    {
        this.folderPath = folderPath;
        topFolder = new FolderStructure(folderPath);
    }

    public FolderStructure getFolder(String[] paths)
    {
        FolderStructure targetStructure = topFolder;
        int startPosition = 0;
        String[] topFolderPaths = topFolder.getPath().split("/");

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

    public String getMetaDataJsonString()
    {
        return new JsonFolder(topFolder).toString();
    }

    public void saveMetadata()
    {
        JsonFolder metadataFolder = new JsonFolder(topFolder);
        try {
            File metadataFile = new File(metadataSavePath);
            if (!(metadataFile.exists())) {
                metadataFile.createNewFile();
            }
            FileWriter writer = new FileWriter(metadataSavePath, false);
            writer.write(metadataFolder.toString());
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public String loadMetadataFromFile()
    {
        String output = "";
        try {
            File targetFile = new File(metadataSavePath);
            Scanner reader = new Scanner(targetFile);
            while (reader.hasNext()) {
                output += reader.next();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void loadFromMetadata(JsonFolder targetFolder)
    {
        JsonFolder[] folders = targetFolder.getFolders();
        for (int i = 0; i < folders.length; i++)
        {
            loadFromMetadata(folders[i]);
        }

        FolderStructure folderStructure = getFolder(targetFolder.getPath());
        List<FileStructure> fileStructures = folderStructure.getFiles();
        JsonFile[] jsonFiles = targetFolder.getFiles();
        for (int i = 0; i < fileStructures.size(); i++)
        {
            for (int k = 0; k < jsonFiles.length; k++)
            {
                if(jsonFiles[k].getPath().equals(fileStructures.get(i).getPath()))
                {
                    fileStructures.get(i).updateFromJsonFile(jsonFiles[k]);
                    break;
                }
            }
        }
    }

    public void loadFromMetadata()
    {
        loadFromMetadata(new JsonFolder(loadMetadataFromFile()));
    }
}
