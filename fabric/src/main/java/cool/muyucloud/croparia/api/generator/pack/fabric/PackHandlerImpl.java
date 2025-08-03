package cool.muyucloud.croparia.api.generator.pack.fabric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import cool.muyucloud.croparia.api.generator.DataGenerator;
import cool.muyucloud.croparia.api.generator.util.JarJarEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackHandlerImpl {
    private static final Pattern PATTERN = Pattern.compile("^data-generators/([^/]+)/([^/]+)/.*\\.cdg$");
    private static final Map<ResourceLocation, Collection<JarJarEntry>> BUILTIN_GENERATORS;

    static {
        Map<ResourceLocation, ArrayList<JarJarEntry>> map = new HashMap<>();
        FabricLoader.getInstance().getAllMods().forEach(mod -> mod.getOrigin().getPaths().forEach(path -> {
            File file = path.toFile();
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try (JarFile jar = new JarFile(path.toFile())) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        Matcher matcher = PATTERN.matcher(name);
                        if (matcher.find()) {
                            ResourceLocation id = ResourceLocation.tryBuild(matcher.group(1), matcher.group(2));
                            if (id == null) {
                                DataGenerator.LOGGER.error("Invalid generator entry \"%s\" in mod \"%s\"".formatted(name, mod.getMetadata().getId()));
                                continue;
                            }
                            ArrayList<JarJarEntry> collected = map.computeIfAbsent(id, k -> new ArrayList<>());
                            collected.add(new JarJarEntry(path.toFile(), entry));
                        }
                    }
                } catch (Throwable t) {
                    DataGenerator.LOGGER.error("Error in reading generators from mod \"%s\"".formatted(mod.getMetadata().getId()), t);
                }
            }
        }));
        ImmutableMap.Builder<ResourceLocation, Collection<JarJarEntry>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, ArrayList<JarJarEntry>> entry : map.entrySet()) {
            builder.put(entry.getKey(), ImmutableList.copyOf(entry.getValue()));
        }
        BUILTIN_GENERATORS = builder.build();
    }

    public static Map<ResourceLocation, Collection<JarJarEntry>> getBuiltinGenerators() {
        return BUILTIN_GENERATORS;
    }
}
