package com.developer.shion;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

@WebServlet(urlPatterns = "/start")
public class Main_Servlet extends HttpServlet {
    public static ArrayList<String[]> RESULT = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BufferedImage bufferedImage = ImageIO.read(req.getInputStream());
            ImageCutter imageCutter = new ImageCutter(bufferedImage);
            imageCutter.CUT();
            BufferedImage bufferedImage1 = imageCutter.SAVE();

            Analyze analyze = new Analyze(bufferedImage1);
            String jsonData = analyze.startAnalyzing();
            CharacterAnalyzer analyzer = new CharacterAnalyzer();
            String[] res = analyzer.getCharacter(jsonData);
            System.out.println(res[0]);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(res[0]);
            resp.getWriter().println(res[1]);
        } catch (IndexOutOfBoundsException e){
            resp.getWriter().println("ERROR");
            resp.getWriter().println("ERROR");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("HELLO");
    }
}