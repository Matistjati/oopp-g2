package FileHandling;

import java.util.Date;

public class FileStructure {
    private String fileName;
    private Date lastModifiedDate;
    public FileStructure(String fileName)
    {
        this.fileName = fileName;
        lastModifiedDate = new Date();
    }

    public String getName()
    {
        return fileName;
    }
    public Date getDate()
    {
        return new Date(lastModifiedDate.getTime());
    }
}

