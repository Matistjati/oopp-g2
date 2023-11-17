package filehandling;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

public class JsonFolder extends JsonAbstractFile {
    final static String folderKey = "folderList";
    final static String fileKey = "fileList";


    public JsonFolder(FolderStructure structure)
    {
        super(structure.getPath());
        ArrayList<FileStructure> fileList = structure.getFiles();
        JsonFile[] fileArray = new JsonFile[fileList.size()];

        for (int i = 0; i < fileList.size(); i++)
        {
            fileArray[i] = new JsonFile(fileList.get(i));
        }
        this.put(fileKey, new JSONArray(fileArray));

        ArrayList<FolderStructure> folderList = structure.getFolders();
        JsonFolder[] FolderArray = new JsonFolder[folderList.size()];

        for (int i = 0; i < folderList.size(); i++)
        {
            FolderArray[i] = new JsonFolder(folderList.get(i));
        }
        this.put(folderKey, new JSONArray(FolderArray));

    }

    public JsonFolder(JSONObject JSONdata)
    {
        super(JSONdata.getString(pathKey));
        this.put(fileKey, JSONdata.get(fileKey));
        this.put(folderKey, JSONdata.get(folderKey));
    }

    public JsonFolder(String JSONData)
    {
        this(new JSONObject(JSONData));
    }

    public JsonFolder[] getFolders()
    {
        JSONArray folderJsonArray = this.getJSONArray(folderKey);
        JsonFolder[] folders = new JsonFolder[folderJsonArray.length()];
        for (int i = 0; i < folderJsonArray.length(); i++)
        {
            folders[i] = new JsonFolder(folderJsonArray.getJSONObject(i));
        }
        return folders;
    }

    public JsonFile[] getFiles()
    {
        JSONArray fileJsonArray = this.getJSONArray(fileKey);
        JsonFile[] files = new JsonFile[fileJsonArray.length()];
        for (int i = 0; i < fileJsonArray.length(); i++)
        {
            files[i] = new JsonFile(fileJsonArray.getJSONObject(i));
        }
        return files;
    }
}
