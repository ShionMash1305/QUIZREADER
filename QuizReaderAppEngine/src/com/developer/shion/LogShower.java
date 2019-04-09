package com.developer.shion;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class LogShower {
    public static boolean STARTED = false;
    public static StyledDocument document;
    public static JFrame frame;
    public static JTextPane tp;
    public static JScrollPane sp;

    public LogShower() {
        if (frame==null){
            frame=new JFrame();
            tp = new JTextPane();
            sp = new JScrollPane(tp);
            frame.getContentPane().add(sp);
            frame.pack();
            frame.setVisible(true);
            document = tp.getStyledDocument();
        }else {
            tp.setText("");
        }
    }

    public static void printT(String s) {
        if (document==null) {
            new LogShower();
        }
        try {
            document.insertString(0, s + "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void clean(){
        tp.setText("");
    }
}
