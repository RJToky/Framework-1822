package etu1822.framework.servlet;

import etu1822.framework.Mapping;
import etu1822.framework.utility.Util;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@WebServlet(name = "FrontServlet", value = "/")
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;

    @Override
    public void init() throws ServletException {
        super.init();

        ClassLoader classLoader = getServletContext().getClassLoader();
        URI uri = null;
        try {
            uri = Objects.requireNonNull(classLoader.getResource("")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File file = new File(uri);
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
