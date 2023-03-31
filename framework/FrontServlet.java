package etu1822.framework.servlet;

import etu1822.framework.Mapping;
import etu1822.framework.ModelView;
import etu1822.framework.utility.Util;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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
                ModelView view = (ModelView) classMapping.getDeclaredMethod(mapping.getMethod()).invoke(obj);
                RequestDispatcher dispatch = request.getRequestDispatcher(view.getView());
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
