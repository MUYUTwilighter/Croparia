package cool.muyucloud.croparia.api.generator.util;

import cool.muyucloud.croparia.api.generator.DataGenerator;

import java.io.File;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarJarEntry {
    private final File file;
    private final JarEntry entry;

    public JarJarEntry(File file, JarEntry entry) {
        this.file = file;
        this.entry = entry;
    }

    public File getFile() {
        return file;
    }

    public JarEntry getEntry() {
        return entry;
    }

    public void forInputStream(Consumer<InputStream> consumer) {
        try (JarFile jar = new JarFile(this.getFile())) {
            try (InputStream stream = jar.getInputStream(this.getEntry())) {
                consumer.accept(stream);
            }
        } catch (Throwable t) {
            DataGenerator.LOGGER.error("Error handling jar entry %s in %s".formatted(this.getEntry(), this.getFile()), t);
        }
    }
}
