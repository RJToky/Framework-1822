package etu1822.framework.utility;

import etu1822.framework.Mapping;
import etu1822.framework.ModelView;
import etu1822.framework.servlet.FrontServlet;
import etu1822.framework.annotation.Scope;
import etu1822.framework.annotation.Url;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;

@SuppressWarnings("unchecked")
public class Util {
    public static String getURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        StringBuilder rep = new StringBuilder();
        String[] array = requestURI.split("/");
        for (int i = 2; i < array.length; i++) {
            rep.append("/").append(array[i]);
        }
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

    public static void uploadFile(Object obj, Field field, HttpServletRequest request) {
        field.setAccessible(true);
        try {
            Part part = request.getPart(field.getName());
            InputStream is = part.getInputStream();
            String name = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            byte[] file = is.readAllBytes();
            FileUpload fileUpload = new FileUpload(name, file);
            field.set(obj, fileUpload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getInstance(FrontServlet frontServlet, Class<?> classMapping) {
        Object obj = null;
        try {
            if (frontServlet.getSingleton().containsKey(classMapping)) {
                obj = frontServlet.getSingleton().get(classMapping);
            } else {
                obj = classMapping.getConstructor().newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static ModelView newModelView(Object obj, String[] parameters, Mapping mapping,
            Class<?> classMapping) {
        ModelView modelView = new ModelView();
        Method[] methods = classMapping.getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().equals(mapping.getMethod())) {
                    if (method.getParameterTypes().length > 0) {
                        Object[] args = Util.convertParameters(parameters, method);
                        modelView = (ModelView) method.invoke(obj, args);
                    } else {
                        modelView = (ModelView) method.invoke(obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return modelView;
    }

    public static void initFrontServlet(FrontServlet frontServlet)
            throws Exception {
        ClassLoader classLoader = frontServlet.getServletContext().getClassLoader();
        URI uri = null;
        try {
            uri = Objects.requireNonNull(classLoader.getResource("")).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File path = new File(uri);

        ArrayList<String> allClassName = new ArrayList<>();
        Util.getAllClassName(path, allClassName);

        for (String className : allClassName) {
            Class<?> classMapping = Class.forName(className);
            Method[] methods = classMapping.getDeclaredMethods();

            if (classMapping.getAnnotation(Scope.class) != null) {
                if (classMapping.getAnnotation(Scope.class).value().equalsIgnoreCase("singleton")) {
                    try {
                        frontServlet.getSingleton().put(classMapping, classMapping.getConstructor().newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Method method : methods) {
                if (method.getAnnotation(Url.class) != null) {
                    String key = method.getAnnotation(Url.class).value();
                    frontServlet.getMappingUrls().putIfAbsent(key, new Mapping(className, method.getName()));
                }
            }
        }
    }

    public static <T> T convert(String value, Class<T> type) {
        PropertyEditorSupport editor = (PropertyEditorSupport) PropertyEditorManager.findEditor(type);
        editor.setAsText(value);
        return (T) editor.getValue();
    }

    public static String[] getParameters(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        ArrayList<String> list = new ArrayList<>();
        while (parameterNames.hasMoreElements()) {
            list.add(parameterNames.nextElement());
        }

        String[] output = new String[list.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = request.getParameter(list.get(i));
        }
        return output;
    }

    public static Object[] convertParameters(String[] parmaters, Method method) {
        Object[] output = new Object[parmaters.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = Util.convert(parmaters[i], method.getParameterTypes()[i]);
        }
        return output;
    }

}