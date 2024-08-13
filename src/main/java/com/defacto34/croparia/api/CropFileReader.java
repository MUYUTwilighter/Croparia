//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api;

import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropType;
import com.defacto34.croparia.init.CropInit;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CropFileReader {
    public CropFileReader() {
    }

    public static void readAllCustomJsons(String folderPath) throws IOException {
        List<Crop> customObjects = new ArrayList();
        String jsonFilePath = folderPath + File.separator + "exemple.json";
        File exempleFile = new File(jsonFilePath);
        if (!exempleFile.exists()) {
        }

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
            if (files != null) {
                File[] var6 = files;
                int var7 = files.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    File file = var6[var8];
                    customObjects.add(readCustomJson(file));
                }
            }
        }

    }

    private static Crop readCustomJson(File file) throws IOException {
        FileReader reader = new FileReader(file);

        Crop var3;
        try {
            JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
            var3 = parseCustomObject(jsonObject);
        } catch (Throwable var5) {
            try {
                reader.close();
            } catch (Throwable var4) {
                var5.addSuppressed(var4);
            }

            throw var5;
        }

        reader.close();
        return var3;
    }

    private static Crop parseCustomObject(JsonObject jsonObject) {
        String name = getStringOrNull(jsonObject, "name");
        int tier = getIntOrDefault(jsonObject, "tier", 0);
        String item = getStringOrNull(jsonObject, "tag");
        int color = getColorAsInt(jsonObject, "color");
        return CropInit.compatCrops(name, CropType.CUSTOM, tier, item, color, "minecraft");
    }

    private static String getStringOrNull(JsonObject jsonObject, String key) {
        return jsonObject.has(key) && jsonObject.get(key).isJsonPrimitive() ? jsonObject.get(key).getAsString() : null;
    }

    private static int getColorAsInt(JsonObject jsonObject, String key) {
        if (jsonObject.has(key) && jsonObject.get(key).isJsonPrimitive()) {
            String colorHex = jsonObject.get(key).getAsString();
            return Integer.parseInt(colorHex.substring(2), 16);
        } else {
            return 0;
        }
    }

    private static int getIntOrDefault(JsonObject jsonObject, String key, int defaultValue) {
        return jsonObject.has(key) && jsonObject.get(key).isJsonPrimitive() ? jsonObject.get(key).getAsInt() : defaultValue;
    }

    public static void main() {
        File gameDir = FabricLoader.getInstance().getGameDirectory();
        String var10000 = gameDir.getAbsolutePath();
        String cropsFolderPath = var10000 + File.separator + "crops";
        System.out.println("folder");

        try {
            readAllCustomJsons(cropsFolderPath);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}
