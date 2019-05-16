package game;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Creates {@link JsonWriter} and {@link JsonReader} to the given file.
 */
@Slf4j
public class FileOperations {

    private String destination;

    /**
     * The constructor of the {@link FileOperations}, gets the destination of the current folder.
     */
    FileOperations() {
        try {
            Path workingDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
            destination = workingDirectory.toString() + File.separator + "data" + File.separator;
            log.info("Working directory: {}", workingDirectory);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * Creates a file if it doesn't exist.
     *
     * @param fileName the name of the file
     * @return a {@link JsonReader} to the file
     */
    public JsonReader CopyFileFromJar(String fileName) {
        try {
            File directory = new File(destination);
            if (directory.mkdir()) {
                log.info("Directory {} created", destination);
            }

            destination = destination + fileName;
            InputStream source = getClass().getResourceAsStream("/" + fileName);
            Path dest = Paths.get(destination);
            if (Files.notExists(dest)) {
                Files.copy(source, Paths.get(destination));
                log.info("File table.json copied from jar to {}", destination);
            } else {
                log.info("{} already exists", destination);
            }
            return new JsonReader(new FileReader(destination));

        } catch (Exception e) {
            log.error(e.toString());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try {
                return new JsonReader(new FileReader(classLoader.getResource("table.json").getPath()));
            } catch (Exception ex) {
                log.error(ex.toString());
                return null;
            }
        }
    }

    /**
     * Creates if it was not created yet.
     *
     * @param fileName the name of the file
     * @return a {@link JsonWriter} to the file
     */
    public JsonWriter CreateJsonWriter(String fileName) {
        try {
            if (destination.substring(destination.length() - 1).equals(File.separator)) {
                destination = destination.concat(fileName);
            }
            return new JsonWriter(new FileWriter(destination));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }
}
