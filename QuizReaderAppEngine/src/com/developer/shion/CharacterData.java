package com.developer.shion;

public class CharacterData {
    public String text;
    public int x1, x2,x3,x4,y1,y2,y3,y4;
    public CharacterData(String text, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        this.x1=x1;
        this.x2=x2;
        this.x3=x3;
        this.x4=x4;
        this.y1=y1;
        this.y2=y2;
        this.y3=y3;
        this.y4=y4;
        this.text=text;
    }

    @Override
    public String toString() {
        StringBuilder response=new StringBuilder();
        response.append("{(").append(x1).append(',').append(y1).append(") (").append(x2).append(',').append(y2).append(") (").append(x3).append(',').append(y3).append(") (").append(x4).append(',').append(y4).append("), \""+text+"\"}");
        return response.toString();
    }
}
