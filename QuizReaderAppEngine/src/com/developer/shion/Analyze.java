package com.developer.shion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Analyze {
    private BufferedImage image;
    private String result;

    public Analyze(BufferedImage image) {
        this.image = image;
    }

    public String startAnalyzing() throws IOException {
      return getJsonData();
    }


    private String getJsonData() throws IOException {
        //This Class collects analyzed data from Google
        URL url = new URL("https://vision.googleapis.com/v1/images:annotate?key=" + "AIzaSyAwNiM_-XLY_Cl-uknmyOqR1EoF0NGQ-TE");
        HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
        urlConnect.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        urlConnect.setRequestMethod("POST");
        urlConnect.setDoOutput(true);
        BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(urlConnect.getOutputStream()));
        outputStreamWriter.write(JsonRequestCreator(image));
        outputStreamWriter.close();
        String responseMes = urlConnect.getResponseMessage();
        StringBuilder response = new StringBuilder();
        if (urlConnect.getInputStream() == null) {
            System.out.println("No stream");
            return null;
        } else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnect.getInputStream(), StandardCharsets.UTF_8));
            String resLine;
            while ((resLine = bufferedReader.readLine()) != null) {
                response.append(resLine);
            }
            return response.toString();
        }
    }

    public String JsonRequestCreator(BufferedImage file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        ImageIO.write(file, "png", bos);
        bos.flush();
        bos.close();
        byte[] bImage = baos.toByteArray();
        byte[] encoded = Base64.getEncoder().encode(bImage);
        String base64Image = new String(encoded);
        String s = "{\"requests\":  [{ \"features\":  [ {\"type\": \"DOCUMENT_TEXT_DETECTION\""
                + "}], \"image\":{\"content\": \"" + base64Image + "\"}}]}";
        return s;
    }

}
