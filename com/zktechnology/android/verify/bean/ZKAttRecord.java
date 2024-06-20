package com.zktechnology.android.verify.bean;

import java.io.Serializable;

public class ZKAttRecord implements Serializable {
    private String Description;
    private String Name;
    private String State_Name;
    private int State_No = 0;
    private int Status;
    private String UserPin;
    private String Verify_Time;
    private int Work_Code_ID;
    private String Work_Code_Name;
    private String Work_Code_Num;
    private int id = -1;

    public String getName() {
        return this.Name;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public String getVerify_Time() {
        return this.Verify_Time;
    }

    public void setVerify_Time(String str) {
        this.Verify_Time = str;
    }

    public int getStatus() {
        return this.Status;
    }

    public void setStatus(int i) {
        this.Status = i;
    }

    public int getWork_Code_ID() {
        return this.Work_Code_ID;
    }

    public void setWork_Code_ID(int i) {
        this.Work_Code_ID = i;
    }

    public int getState_No() {
        return this.State_No;
    }

    public void setState_No(int i) {
        this.State_No = i;
    }

    public String getState_Name() {
        return this.State_Name;
    }

    public void setState_Name(String str) {
        this.State_Name = str;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String str) {
        this.Description = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getWork_Code_Num() {
        return this.Work_Code_Num;
    }

    public void setWork_Code_Num(String str) {
        this.Work_Code_Num = str;
    }

    public String getWork_Code_Name() {
        return this.Work_Code_Name;
    }

    public void setWork_Code_Name(String str) {
        this.Work_Code_Name = str;
    }

    public String getUserPin() {
        return this.UserPin;
    }

    public void setUserPin(String str) {
        this.UserPin = str;
    }
}
