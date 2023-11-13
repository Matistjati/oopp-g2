import java.nio.file.Path;

public class FileTypeResolver {
    public static String resolveFileType(Path path) {
        String fileName = path.getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        return switch (extension) {
            case "html" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "text/javascript";
            default -> "application/octet-stream";
        };
    }
}
