package etu1822.framework.servlet;

import etu1822.framework.Mapping;
import etu1822.framework.ModelView;
import etu1822.framework.utility.Util;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;

@WebServlet(name = "FrontServlet", value = "/")
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Util.initMappingUrls(getServletContext(), mappingUrls);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = Util.getURI(request);

            if (mappingUrls.containsKey(url)) {
                Mapping mapping = mappingUrls.get(url);
                Class<?> classMapping = Class.forName(mapping.getClassName());
                Object obj = classMapping.getConstructor().newInstance();

                Field[] fields = classMapping.getDeclaredFields();
                for (Field field : fields) {
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
}
