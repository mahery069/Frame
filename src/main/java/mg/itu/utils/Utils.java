package mg.itu.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
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