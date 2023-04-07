package etu1822.framework.utility;

import etu1822.framework.Mapping;
import etu1822.framework.annotation.Url;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Util {
    public static String getURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        StringBuilder rep = new StringBuilder();
        System.out.println(requestURI);
        String[] array = requestURI.split("/");
        for (int i = 2; i < array.length; i++) {
            rep.append("/").append(array[i]);
        }
        System.out.println(rep.toString());
        return rep.toString();
    }

    public static void getAllClassName(File path, ArrayList<String> allClassName) {
        String className;

        for (File file : Objects.requireNonNull(path.listFiles())) {
            if (file.isDirectory()) {
                getAllClassName(file, allClassName);
            } else {
                className = file.getPath().replace(".class", "");

                if (className.contains("\\")) {
                    className = className.split("\\\\WEB-INF\\\\classes\\\\")[1];
                    className = className.replace("\\", ".");
                } else {
                    className = className.split("/WEB-INF/classes/")[1];
                    className = className.replace("/", ".");
                }
                allClassName.add(className);
            }
        }
    }

    public static void initMappingUrls(ServletContext servletContext, HashMap<String, Mapping> mappingUrls)
            throws ClassNotFoundException {
        ClassLoader classLoader = servletContext.getClassLoader();
        URI uri = null;
        try {
            uri = Objects.requireNonNull(classLoader.getResource("")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File path = new File(uri);

        ArrayList<String> allClassName = new ArrayList<>();
        Util.getAllClassName(path, allClassName);

        for (String className : allClassName) {
            Class<?> classTemp = Class.forName(className);
            Method[] methods = classTemp.getDeclaredMethods();

            for (Method method : methods) {
                if (method.getAnnotation(Url.class) != null) {
                    String key = method.getAnnotation(Url.class).value();
                    mappingUrls.putIfAbsent(key, new Mapping(className, method.getName()));
                }
            }
        }
    }

}