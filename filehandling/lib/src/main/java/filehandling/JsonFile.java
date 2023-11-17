package filehandling;

import org.json.JSONObject;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class JsonFile extends JsonAbstractFile{
    final static String modifiedDateKey = "modifiedDate";
    public JsonFile(FileStructure structure)
    {
        super(structure.getPath());
        this.put(modifiedDateKey, structure.getDate());
    }

    public JsonFile(JSONObject JSONdata)
    {
        super(JSONdata.getString(pathKey));
        this.put(modifiedDateKey, JSONdata.get(modifiedDateKey));
    }

    public Date getModifiedDate()
    {
        //return new Date(this.getString("modifiedDate")); TODO: This is broken for some reason. Figure out why
        return new Date();
    }

    public JsonFile(String JSONData)
    {
        this(new JSONObject(JSONData));
    }
}
