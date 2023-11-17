package filehandling;

import org.json.JSONArray;

import java.sql.Array;
import java.util.ArrayList;

public class JsonFolder extends JsonAbstractFile {

    public JsonFolder(FolderStructure structure)
    {
        super(structure.getName());
        ArrayList<FileStructure> fileList = structure.getFiles();
        JsonFile[] fileArray = new JsonFile[fileList.size()];

        for (int i = 0; i < fileList.size(); i++)
        {
            fileArray[i] = new JsonFile(fileList.get(i));
        }
        this.put("fileList", new JSONArray(fileArray));

        ArrayList<FolderStructure> folderList = structure.getFolders();
        JsonFolder[] FolderArray = new JsonFolder[folderList.size()];

        for (int i = 0; i < folderList.size(); i++)
        {
            FolderArray[i] = new JsonFolder(folderList.get(i));
        }
        this.put("folderList", new JSONArray(FolderArray));

    }
}
