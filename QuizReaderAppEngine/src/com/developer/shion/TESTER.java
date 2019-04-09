package com.developer.shion;

import com.sun.mail.iap.ByteArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TESTER {
    private static ArrayList<String[]> results = new ArrayList<>();
    private static int finished = 0;

    public static void main(String... args) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:\\Users\\shion\\OneDrive\\デスクトップ"));
        chooser.setMultiSelectionEnabled(true);
        chooser.setDialogTitle("ファイルを選択してください。");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File[] file = chooser.getSelectedFiles();
            results=new ArrayList<>(file.length);
            ExecutorService exec = Executors.newFixedThreadPool(10);
            for (int i = 0; i < file.length; i++) {
                int finalI = i;
                System.out.println("SUBMIT");
                exec.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String[] res = null;
                            while (res == null) {
                                res = START(file[finalI]);
                                System.out.println("TEST1");
                            }
                            System.out.println("TEST2");
                            results.add(res);
                            finished++;
                            System.out.println(finished);
                            System.out.println(file.length);
                            if (finished == file.length) {
                                for (int x = 0; x < file.length; x++) {
                                    System.out.println(results.get(x)[0]+"  :   "+results.get(x)[1]);
                                }
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                            System.out.println("ERROR");
                        }
                    }
                });
                System.out.println("SUBMITTED");
            }
        }

    }

    public static String[] START(File file) throws IOException {
        byte[] data = Files.readAllBytes(file.toPath());
        URL url = new URL("https://quizreader.appspot.com/start");
//            URL url=new URL("http://localhost:8080/start");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "text/plain; charset=utf-8");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED || connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println(connection.getResponseMessage());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String[] result = {reader.readLine(), reader.readLine()};
            System.out.println(result[0]);
            System.out.println(result[1]);
            return result;
        } else {
            return null;
        }
    }
}
