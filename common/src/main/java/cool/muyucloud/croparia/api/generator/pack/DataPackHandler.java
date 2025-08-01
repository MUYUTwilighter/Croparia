package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.util.AlwaysEnabledFileResourcePackProvider;
import cool.muyucloud.croparia.util.Util;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DataPackHandler extends PackHandler {
    public static final Map<ResourceLocation, DataPackHandler> REGISTRY = new HashMap<>();

    public static <P extends DataPackHandler> P register(P pack) {
        REGISTRY.put(pack.getId(), pack);
        return pack;
    }

    public static DataPackHandler register(ResourceLocation id, Path path, JsonObject meta, Supplier<Boolean> override) {
        return register(new DataPackHandler(id, path, meta, override));
    }

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        this.getId().toString(), getRoot(), PackType.SERVER_DATA, PackSource.BUILT_IN
    );

    public DataPackHandler(ResourceLocation id, Path path, JsonObject meta, Supplier<Boolean> override) {
        super(id, path, meta, override);
    }

    public AlwaysEnabledFileResourcePackProvider getDatapack() {
        return datapack;
    }

    @Override
    public void clear() {
        Path path = this.root.resolve("data");
        File file = path.toFile();
        if (file.isDirectory()) {
            try {
                Util.deleteDir(file);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to clear data pack directory", e);
            }
        }
    }

    public void moveBuiltInGenerators() {
        try {
            Path targetDir = this.root.resolve("generators");
            File targetDirFile = targetDir.toFile();
            if (!targetDirFile.isDirectory() && !targetDirFile.mkdirs()) {
                throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(targetDir));
            }
            Enumeration<URL> urls = CropariaIf.class.getClassLoader().getResources("data_generators");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String jarPath = unifyUrl(url);
                try (JarFile jar = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().startsWith("croparia-if-generators/") && !entry.isDirectory()) {
                            String filename = entry.getName().substring("croparia-if-generators/".length());
                            File targetFile = targetDir.resolve(filename).toFile();
                            if (!targetFile.isFile() || Platform.isDevelopmentEnvironment()) {
                                try (OutputStream stream = new FileOutputStream(targetFile)) {
                                    jar.getInputStream(entry).transferTo(stream);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            LOGGER.error("Failed to move built-in generators", e);
        }
    }

    private static @NotNull String unifyUrl(URL url) {
        // Compat with encoding
        String urlPath;
        try {
            urlPath = URLDecoder.decode(url.getPath(), Charset.defaultCharset());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to decode URL", e);
        }
        // Extract jar path
        String jarPath = urlPath.substring(urlPath.indexOf("/") + 1, urlPath.lastIndexOf(".jar") + 4);
        // Compat with file system that require "/" prefix
        if (!Path.of(jarPath).isAbsolute()) {
            jarPath = "/" + jarPath;
        }
        return jarPath;
    }
}