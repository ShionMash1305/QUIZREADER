package com.developer.shion;

public class ARGB {
    private int RGB;
    private String[] ARGBs;
    private int[] ARGBsI;
    private int Alpha;

    public ARGB(Integer alpha, int rgb) {
        RGB = rgb;
        Alpha =alpha;
        String s = Integer.toHexString(RGB);
        ARGBs = new String[]{s.substring(0, 2), s.substring(2, 4), s.substring(4, 6), s.substring(6)};
        ARGBsI = new int[]{Integer.parseInt(ARGBs[0], 16), Integer.parseInt(ARGBs[1], 16), Integer.parseInt(ARGBs[2], 16), Integer.parseInt(ARGBs[3], 16)};
    }

    public ARGB(int A, int R, int G, int B) {
        ARGBsI = new int[]{A, R, G, B};
        ARGBs = new String[]{Integer.toHexString(A), Integer.toHexString(A), Integer.toHexString(A), Integer.toHexString(A)};
        Alpha =A;

    }

    public String[] getARGBs() {
        return ARGBs;
    }

    public boolean similarToWhite() {
        return ARGBsI[1] < 30 && ARGBsI[2] < 30 && ARGBsI[3] < 30;
    }
}
