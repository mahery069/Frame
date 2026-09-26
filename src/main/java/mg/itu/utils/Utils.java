package mg.itu.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class Utils {
    public static String toJson(Object value) {
        StringBuilder builder = new StringBuilder();
        Set<Object> visited = java.util.Collections.newSetFromMap(new IdentityHashMap<>());
        appendJson(builder, value, visited);
        return builder.toString();
    }

    private static void appendJson(StringBuilder builder, Object value, Set<Object> visited) {
        if (value == null) {
            builder.append("null");
            return;
        }

        if (value instanceof String || value instanceof Character) {
            builder.append('"').append(escapeJson(value.toString())).append('"');
            return;
        }

        if (value instanceof Number || value instanceof Boolean) {
            builder.append(value.toString());
            return;
        }

        if (value.getClass().isEnum()) {
            builder.append('"').append(escapeJson(value.toString())).append('"');
            return;
        }

        if (value.getClass().isArray()) {
            builder.append('[');
            int length = Array.getLength(value);
            for (int index = 0; index < length; index++) {
                if (index > 0) {
                    builder.append(',');
                }
                appendJson(builder, Array.get(value, index), visited);
            }
            builder.append(']');
            return;
        }

        if (value instanceof Collection<?>) {
            builder.append('[');
            boolean first = true;
            for (Object element : (Collection<?>) value) {
                if (!first) {
                    builder.append(',');
                }
                appendJson(builder, element, visited);
                first = false;
            }
            builder.append(']');
            return;
        }

        if (value instanceof Map<?, ?>) {
            builder.append('{');
            boolean first = true;
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                if (!first) {
                    builder.append(',');
                }
                builder.append('"')
                        .append(escapeJson(String.valueOf(entry.getKey())))
                        .append('"')
                        .append(':');
                appendJson(builder, entry.getValue(), visited);
                first = false;
            }
            builder.append('}');
            return;
        }

        if (visited.contains(value)) {
            builder.append("null");
            return;
        }

        visited.add(value);
        builder.append('{');
        boolean first = true;
        Class<?> current = value.getClass();
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (!first) {
                    builder.append(',');
                }
                field.setAccessible(true);
                builder.append('"')
                        .append(escapeJson(field.getName()))
                        .append('"')
                        .append(':');
                try {
                    appendJson(builder, field.get(value), visited);
                } catch (IllegalAccessException e) {
                    builder.append("null");
                }
                first = false;
            }
            current = current.getSuperclass();
        }
        builder.append('}');
        visited.remove(value);
    }

    private static String escapeJson(String value) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '"':
                    builder.append("\\\"");
                    break;
                case '\\':
                    builder.append("\\\\");
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                default:
                    if (character < 0x20) {
                        builder.append(String.format("\\u%04x", (int) character));
                    } else {
                        builder.append(character);
                    }
            }
        }
        return builder.toString();
    }

    public static List<String> findClassesByAnnotation(
            String packageName,
            Class<? extends Annotation> annotation) {
        List<String> result = new ArrayList<>();
        String path = packageName.replace('.', '/');
        
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);
            
            if (resource == null) {
                return result;
            }
            
            File directory = new File(resource.getFile());
            if (!directory.exists()) {
                return result;
            }
            
            scanDirectory(directory, packageName, annotation, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    private static void scanDirectory(File directory, String packageName, 
                                      Class<? extends Annotation> annotation, List<String> result) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        
        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName(), annotation, result);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + 
                    file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(annotation)) {
                        result.add(className);
                    }
                } catch (ClassNotFoundException e) {
                    // Ignore classes that can't be loaded
                }
            }
        }
    }
}