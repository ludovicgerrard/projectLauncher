package com.zktechnology.android.verify.bean;

import java.io.Serializable;

public class ZKPersonMsg implements Serializable {
    private String Content;
    private String Start_Time;
    private int Valid_Time;

    public ZKPersonMsg() {
    }

    public ZKPersonMsg(String str, int i, String str2) {
        this.Start_Time = str;
        this.Valid_Time = i;
        this.Content = str2;
    }

    public String getStart_Time() {
        return this.Start_Time;
    }

    public void setStart_Time(String str) {
        this.Start_Time = str;
    }

    public int getValid_Time() {
        return this.Valid_Time;
    }

    public void setValid_Time(int i) {
        this.Valid_Time = i;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String str) {
        this.Content = str;
    }
}
