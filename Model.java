import FileHandling.FileHandler;

public class Model {
    private FileHandler fileHandler;
    public Model(String folderPath)
    {
        fileHandler = new FileHandler(folderPath);
    }
}
