package etu1822.framework.utility;

import jakarta.servlet.http.HttpServletRequest;

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
}
