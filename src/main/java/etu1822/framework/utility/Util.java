package etu1822.framework.utility;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class Util {
    public  static String getURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        StringBuilder rep = new StringBuilder();

        String[] array = requestURI.split("/");
        for (int i = 2; i < array.length; i++) {
            rep.append(array[i]).append("/");
        }
        return rep.toString();
    }

    public static void getAllClassName(File path, ArrayList<String> allClassName) {
        String className;

        for(File file : Objects.requireNonNull(path.listFiles())) {
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

}