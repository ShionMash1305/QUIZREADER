package com.developer.shion;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImageCutter {

    private BufferedImage image;
    private int height_CUT;
    private int height_CUT_END;
    private BufferedImage processedImage;

    public ImageCutter(BufferedImage image) {
        assert image != null;
        this.image = image;
    }

    public void CUT() {
        int height = 0;
        boolean notComplete = true;
        while (notComplete) {
            int white = 0;
            for (int i = 0; i < image.getWidth(); i++) {
                if (isWhiteRGB(image.getRGB(i, height))) {
                    white++;
                }
            }
            if (((long) white / image.getWidth()) < 0.8) {
                height++;
            } else {
                notComplete = false;
            }
        }
        notComplete = true;
        int height_end = height;
        while (notComplete) {
            int white = 0;
            for (int i = 0; i < image.getWidth(); i++) {
                if (isWhiteRGB(image.getRGB(i, height_end))) {
                    white++;
                }
            }
            BigDecimal b1 = new BigDecimal(white);
            BigDecimal b2 = new BigDecimal(image.getWidth());
            if (b1.divide(b2, 2, RoundingMode.HALF_UP).doubleValue() > 0.3) {
                height_end++;
            } else {
                notComplete = false;
            }
        }
        this.height_CUT = height;
        this.height_CUT_END = height_end;


    }


    public static boolean isWhiteRGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        if (red > 110 && green > 110 && blue > 110) {
            return true;
        } else {
            return false;
        }
    }

    public BufferedImage SAVE() {
        processedImage = image.getSubimage(0, height_CUT, image.getWidth(), height_CUT_END - height_CUT);
        return processedImage;
    }

    public BufferedImage getData() {
        assert processedImage != null;
        return processedImage;
    }


    public void process() {
        int starts = -1;
        int ends = -1;
        for (int i1 = 0; image.getHeight() > i1; i1++) {
            boolean ability = true;
            for (int i2 = 0; i2 < image.getWidth() && ability; i2++) {
                if (!new ARGB(null, image.getRGB(i2, i1)).similarToWhite()) {
                    ability = false;
                }
            }
            if (ability) {
                if (starts == -1) {
                    starts = i1;
                }
            } else {
                if (starts != -1 && ends == -1) {
                    ends = i1;
                }
            }
        }

    }

}
