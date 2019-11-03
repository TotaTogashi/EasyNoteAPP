package com.example.easynote;

public class Item {
    private String title,body,date;
    private int style;

    public Item(String title,String body,String date,int style){
        this.title=title;
        this.body=body;
        this.date=date;
        this.style=style;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public int getStyle() {
        return style;
    }
}
