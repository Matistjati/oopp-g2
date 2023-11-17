package filehandling;

import org.json.JSONObject;

import java.util.Date;

public class JsonFile extends JsonAbstractFile{
    private Date modifiedDate;
    public JsonFile(FileStructure structure)
    {
        super(structure.getPath());
        this.put("modifiedDate", structure.getDate());
    }

    public JsonFile(JSONObject JSONdata)
    {
        super(JSONdata.getString("path"));
        this.put("modifiedDate", JSONdata.get("modifiedDate"));
    }

    public JsonFile(String JSONData)
    {
        this(new JSONObject(JSONData));
    }
}
