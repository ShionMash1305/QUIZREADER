package com.developer.shion;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Properties;
@WebServlet(name="Home",value = "/home")
public class Home extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Properties properties = System.getProperties();
        response.setContentType("text/plain");
        response.getWriter().println("Hello App Engine - Standard using "
                + " Java "
                + properties.get("java.specification.version"));
    }

    public static String getInfo() {
        return "Version: " + System.getProperty("java.version")
                + " OS: " + System.getProperty("os.name")
                + " User: " + System.getProperty("user.name");
    }
}
