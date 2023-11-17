package filehandling;

import java.util.Date;

public class JsonFile extends JsonAbstractFile{
    private Date modifiedDate;
    public JsonFile(FileStructure structure)
    {
        super(structure.getPath());
        this.put("modifiedDate", structure.getDate());
    }
}
