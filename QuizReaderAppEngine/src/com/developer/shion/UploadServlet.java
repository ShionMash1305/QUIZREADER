package com.developer.shion;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name="UploadServlet",value = "/upload")
@MultipartConfig(maxFileSize=33554432)
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String id = req.getParameter("id");
        Part fPart = req.getPart("file");
        response.getWriter().println(getFileName(fPart));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().println("HELLO");
        super.doGet(req, resp);
    }

    private ArrayList<String> getFileName(Part part) {
        String name = null;
        ArrayList<String> fileNames=new ArrayList<>();
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                fileNames.add(name);
            }
        }
        System.out.println(fileNames);
        return fileNames;
    }
}
