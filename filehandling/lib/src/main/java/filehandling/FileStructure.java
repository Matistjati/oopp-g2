package filehandling;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileStructure {
    private String filePath;
    private Date lastModifiedDate;
    private ArrayList<FileLocks> fileLocks = new ArrayList<>();
    public FileStructure(String filePath)
    {
        this.filePath = filePath;
        lastModifiedDate = new Date();
        File targetFile = new File(filePath);
        if (!(targetFile.exists()))
        {
            try{
                targetFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    public String getPath()
    {
        return filePath;
    }
    public String getName() {
        String[] pathSegments = filePath.split("/");
        return pathSegments[pathSegments.length - 1];
    }
    public Date getDate()
    {
        return new Date(lastModifiedDate.getTime());
    }

    public void delete()
    {
        fileLocks.add(FileLocks.delete);
        File TargetFile = new File(filePath);
        TargetFile.delete();
        fileLocks.remove(FileLocks.delete);
    }

    public void write(String data)
    {
        fileLocks.add(FileLocks.write);
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(data);
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        lastModifiedDate = new Date();
        fileLocks.remove(FileLocks.write);
    }

    public String read()
    {
        String output = "";
        fileLocks.add(FileLocks.read);
        try {
            File targetFile = new File(filePath);
            Scanner reader = new Scanner(targetFile);
            while (reader.hasNext()) {
                output += reader.next();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        fileLocks.remove(FileLocks.read);
        return output;
    }

    public FileInputStream getFileInputStream()
    {
        try
        {
            return new FileInputStream(filePath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void updateFromJsonFile(JsonFile updateSource)
    {
        this.lastModifiedDate = updateSource.getModifiedDate();
    }

    enum FileLocks {
        read,
        write,
        delete
    }
}

