package etu1822.framework.servlet;

import etu1822.framework.Mapping;
import etu1822.framework.ModelView;
import etu1822.framework.utility.Util;
import etu1822.framework.utility.FileUpload;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(name = "FrontServlet", value = "*.do")
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls = new HashMap<>();
    HashMap<Class, Object> singleton = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Util.initFrontServlet(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = Util.getURI(request);

            if (this.mappingUrls.containsKey(url)) {
                Mapping mapping = this.mappingUrls.get(url);
                Class<?> classMapping = Class.forName(mapping.getClassName());

                Object obj = null;
                if (this.singleton.containsKey(classMapping)) {
                    obj = this.singleton.get(classMapping);
                } else {
                    obj = classMapping.getConstructor().newInstance();
                }

                Field[] fields = classMapping.getDeclaredFields();
                for (Field field : fields) {

                    if (field.getType() == FileUpload.class) {
                        field.setAccessible(true);
                        try {
                            Part part = request.getPart(field.getName());
                            InputStream is = part.getInputStream();
                            String name = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                            byte[] file = is.readAllBytes();
                            FileUpload fileUpload = new FileUpload(name, file);
                            field.set(obj, fileUpload);
                        } catch (Exception e) {
                        }
                    }

                    if (request.getParameter(field.getName()) != null) {
                        field.setAccessible(true);
                        String value = request.getParameter(field.getName());
                        if (value != null) {
                            Object convertedValue = Util.convert(value, field.getType());
                            field.set(obj, convertedValue);
                        }
                    }
                }

                ModelView modelView = new ModelView();
                String[] parameters = Util.getParameters(request);
                Method[] methods = classMapping.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals(mapping.getMethod())) {
                        if (method.getParameterTypes().length > 0) {
                            Object[] args = Util.convertParameters(parameters, method);
                            modelView = (ModelView) method.invoke(obj, args);
                        } else {
                            modelView = (ModelView) method.invoke(obj);
                        }
                    }
                }

                HashMap<String, Object> data = modelView.getData();
                for (Entry<String, Object> entrySet : data.entrySet()) {
                    request.setAttribute((String) entrySet.getKey(), entrySet.getValue());
                }

                RequestDispatcher dispatch = request.getRequestDispatcher(modelView.getView());
                dispatch.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }

    public HashMap<Class, Object> getSingleton() {
        return singleton;
    }

}
