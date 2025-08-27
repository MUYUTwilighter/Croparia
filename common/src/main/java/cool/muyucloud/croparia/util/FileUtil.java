package cool.muyucloud.croparia.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.function.Consumer;

public class FileUtil {
    public static void write(File file, String content, boolean override) throws IOException {
        File parent = file.getParentFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
            throw new IllegalStateException("Failed to establish parent directory for " + file);
        }
        if (!file.isFile() || override) {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        }
    }

    public static void forFilesIn(File path, Consumer<File> consumer) throws IllegalStateException {
        if (!path.isDirectory() && !path.mkdirs())
            throw new IllegalStateException("Failed to establish directory " + path);
        File[] files = path.listFiles();
        if (files == null) throw new IllegalStateException("Failed to list directory " + path);
        for (File file : files) {
            if (file.isFile()) consumer.accept(file);
            if (file.isDirectory()) forFilesIn(file, consumer);
        }
    }

    public static void deleteUnder(File dir) throws IOException {
        if (dir.isDirectory()) {
            for (File child : Objects.requireNonNull(dir.listFiles())) {
                deleteDir(child);
            }
        }
    }

    public static void deleteDir(File dir) throws IOException {
        if (dir.isDirectory()) {
            for (File child : Objects.requireNonNull(dir.listFiles())) {
                deleteDir(child);
            }
        }
        Files.delete(dir.toPath());
    }
}
