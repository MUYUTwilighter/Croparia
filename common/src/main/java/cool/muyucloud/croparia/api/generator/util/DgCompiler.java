package cool.muyucloud.croparia.api.generator.util;

import com.google.gson.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DgCompiler {
    private static final Pattern META_PATTERN = Pattern.compile(
        "@([^=]+)=((?:[^@;]|@(?!\\w+=)|;(?!\\s*$))*);", Pattern.DOTALL
    );
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Map<String, JsonElement> extractMeta(String cdg) {
        Map<String, JsonElement> result = new HashMap<>();
        Matcher matcher = META_PATTERN.matcher(cdg);
        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = matcher.group(2).trim();
            result.put(key, parseValue(value));
        }
        return result;
    }

    private static String extractTemplate(String cdg) {
        return cdg.replaceAll("@[^=]+=(?:[^@;]|@(?!\\w+=)|;(?!\\s*$))*;", "").trim();
    }

    public static JsonObject compile(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        JsonObject result = compile(new String(stream.readAllBytes()));
        stream.close();
        return result;
    }

    public static JsonObject compile(String cdg) {
        JsonObject result = new JsonObject();
        Map<String, JsonElement> meta = extractMeta(cdg);
        String template = extractTemplate(cdg);
        for (Map.Entry<String, JsonElement> entry : meta.entrySet()) result.add(entry.getKey(), entry.getValue());
        result.add("template", new JsonPrimitive(template));
        return result;
    }

    private static JsonElement parseValue(String valueStr) {
        if (valueStr.equals("true")) return new JsonPrimitive(true);
        else if (valueStr.equals("false")) return new JsonPrimitive(false);
        else if (valueStr.equals("null")) return JsonNull.INSTANCE;
        else if (valueStr.matches("-?\\d+")) return new JsonPrimitive(Integer.parseInt(valueStr));
        else if (valueStr.startsWith("{") && valueStr.endsWith("}")) {
            try {
                return GSON.fromJson(valueStr, JsonObject.class);
            } catch (Throwable t) {
                return new JsonPrimitive(valueStr);
            }
        } else if (valueStr.startsWith("[") && valueStr.endsWith("]")) {
            try {
                return GSON.fromJson(valueStr, JsonArray.class);
            } catch (Throwable t) {
                return new JsonPrimitive(valueStr);
            }
        } else if (valueStr.startsWith("\"") && valueStr.endsWith("\"") || valueStr.startsWith("'") && valueStr.endsWith("'"))
            return new JsonPrimitive(valueStr.substring(1, valueStr.length() - 1));
        else if (valueStr.startsWith("'''") && valueStr.endsWith("'''"))
            return new JsonPrimitive(valueStr.substring(3, valueStr.length() - 3));
        else return new JsonPrimitive(valueStr);
    }
}