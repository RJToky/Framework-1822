package etu1822.framework.servlet;

import etu1822.framework.Mapping;
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

        System.out.println(mappingUrls.get("emp-all").getClassName());
        System.out.println(mappingUrls.get("emp-all").getMethod());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            String url = Util.getURI(request);
            out.println(url);

        } catch (Exception ignored) { }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
