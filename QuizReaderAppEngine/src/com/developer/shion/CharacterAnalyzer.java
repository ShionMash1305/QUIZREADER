package com.developer.shion;

import java.util.ArrayList;

public class CharacterAnalyzer {

    String input = "";
    int position = 0;
    ArrayList<CharacterData> characterData = new ArrayList<CharacterData>();

    public CharacterAnalyzer() {

    }

    public String[] getCharacter(String jsonInput) throws IndexOutOfBoundsException {
        System.out.println(jsonInput);
        input = jsonInput;
//        System.out.println("length: " + input.length());


        //最初に textAnnotationsのところにpositionを合わせる
        position = input.indexOf("\"textAnnotations\"") + 19;

        //読み込みを始める前に終わりのポイントを定める。
        int endPoint = position;
        int level1 = 1;
        while (level1 != 0) {
            endPoint++;
            if (input.charAt(endPoint) == '[') {
                level1++;
            } else if (input.charAt(endPoint) == ']') {
                level1--;
            }
        }
        System.out.println(input.charAt(endPoint));
        System.out.println("endPoint: " + endPoint);


        //読み取り開始地点へ
        boolean continue1 = true;
        int level = 0;
        while (continue1) {
            position++;
            if (input.charAt(position) == '{') {
                level++;
            } else if (input.charAt(position) == '}') {
                level--;
            }
            if (level == 0) {
                continue1 = false;
            }
        }
        position++;
        position++;

        //ここで最初の読み取りポイントへ。


        boolean keepRead = true;


        //読み取りを開始
        while (keepRead) {
            int desPosition = input.indexOf("\"description\":", position);
            System.out.println("DES " + desPosition);
            if (desPosition > endPoint || desPosition == -1) {
                keepRead = false;
            } else {
                String text = readValue(desPosition + 14);
                System.out.println("TEXT: \"" + text + "\"");

                int x1 = readIntValue(input.indexOf("\"x\":", position) + 4);
                int y1 = readIntValue(input.indexOf("\"y\":", position) + 4);
                int x2 = readIntValue(input.indexOf("\"x\":", position) + 4);
                int y2 = readIntValue(input.indexOf("\"y\":", position) + 4);
                int x3 = readIntValue(input.indexOf("\"x\":", position) + 4);
                int y3 = readIntValue(input.indexOf("\"y\":", position) + 4);
                int x4 = readIntValue(input.indexOf("\"x\":", position) + 4);
                int y4 = readIntValue(input.indexOf("\"y\":", position) + 4);
                CharacterData data1 = new CharacterData(text, x1, y1, x2, y2, x3, y3, x4, y4);
                System.out.println("DATA " + characterData.size() + ": " + data1.toString());
                characterData.add(data1);
            }
        }
        System.out.println(characterData);
        //ここまでで読み取りが完了
        //ここからは情報の整理にあたる。
        //まずは"問題"を見つける

        CharacterData questionLabel = null;

        boolean c = true;
        int i = 1;
        while (c) {
            System.out.println(i);
            if (characterData.get(i).text.contains("問題")) {
                c = false;
                questionLabel = characterData.get(i);
//                System.out.println(characterData.get(i).toString());
            } else {
                i++;
            }
        }
//        System.out.println(characterData.get(i).text);

        ArrayList<CharacterData> question = new ArrayList<>();
        c = true;
        //一番最初のQuestion行を取り出す。
        while (c) {
            i++;
            if (!characterData.get(i).text.contains("解答") && characterData.get(i).y1 - questionLabel.y4 < 80 && characterData.get(i).y1 - questionLabel.y4 > 0) {
                c = false;
                question.add(characterData.get(i));
//                System.out.println("FIRST QUESTION");
                System.out.println(characterData.get(i));
            }
        }
        c = true;
        while (c) {
            i++;
            if (!characterData.get(i).text.contains("解答") && characterData.get(i).y1 - question.get(question.size() - 1).y1 < 70 && characterData.get(i).y1 - question.get(question.size() - 1).y1 > -5) {
                question.add(characterData.get(i));
                System.out.println(characterData.get(i));
            } else {
                c = false;
            }
        }


        //ここでデータの処理が完了したのでここからプロセス

        System.out.println(question);

        //ここからは解答を引っ張ってきます。

        ArrayList<CharacterData> answer = new ArrayList<>();

        CharacterData answerLabel = null;
        c = true;
        i = 1;
        while (c) {
            if (characterData.get(i).text.contains("解答")) {
                c = false;
                answerLabel = characterData.get(i);
//                System.out.println(characterData.get(i).toString());
            }
            i++;
        }
//        System.out.println(answerLabel);
        c = true;
        i = 0;
        while (c) {
            CharacterData charData = characterData.get(i);
//            System.out.println(String.valueOf(charData.y1 - answerLabel.y4 < 80) + "," + String.valueOf(charData.y1 > answerLabel.y4) + "," + charData.text);
            if (charData.y1 - answerLabel.y4 < 80 && charData.y1 > answerLabel.y4&&charData.x1>25) {
                answer.add(charData);
                c = false;
            } else {
                i++;
            }
        }
        c = true;
        i++;
        while (c) {
            CharacterData charData = characterData.get(i);
            if (charData.y1 - answer.get(answer.size() - 1).y1 < 20 && charData.y1 - answer.get(answer.size() - 1).y1 > -5&&charData.x1>20) {
//                System.out.println(charData);
                answer.add(charData);

            }
            i++;
            if (i == characterData.size()) {
                c = false;
            }

        }
        i = 0;
        while (c) {
            boolean c1 = true;
            for (int i2 = 0; i2 < characterData.size() && c1; i2++) {
                CharacterData charData = characterData.get(i2);
                CharacterData lastAnsData = answer.get(answer.size() - 1);
                if (charData.y1 - lastAnsData.y4 < 60 && charData.y1 > lastAnsData.y4) {
//                    System.out.println(charData);
                    answer.add(charData);
                } else if (i2 == characterData.size() - 1) {
                    c = false;
                }
            }
        }
        System.out.println(answer);


        //データの処理がすべて完了したので、文字列をString[]で返します。
        String[] strings = new String[]{appendValue(question), appendValue(answer)};

        return strings;

    }

    public String appendValue(ArrayList<CharacterData> data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            result.append(data.get(i).text);
        }
        return result.toString();
    }

    public String readValue(int startingPosition) {
        int start = input.indexOf('"', startingPosition);
        int end = input.indexOf('"', start + 1);
        position = end + 1;
        String r = input.substring(start + 1, end);
        System.out.println(r);
        return input.substring(start + 1, end);
    }

    public int readIntValue(int startingPosition) {
        while (!isNumeric(input.charAt(startingPosition))) {
            startingPosition++;
        }
        int end = startingPosition;
        while (isNumeric(input.charAt(end))) {
            end++;
        }
        position = end + 1;
        return Integer.parseInt(input.substring(startingPosition, end));
    }


    public boolean isNumeric(int ch) {
        if (ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9' || ch == '0') {
            return true;
        } else {
            return false;
        }
    }
}
