package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "SHORT_STATE")
public class ShortState extends AbstractORM<ShortState> implements Serializable {
    private static final long serialVersionUID = 1512784716679L;
    @DatabaseField(columnName = "Auto_Change")
    private int Auto_Change;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Description")
    private String Description;
    @DatabaseField(columnName = "Fri")
    private int Fri;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Mon")
    private int Mon;
    @DatabaseField(columnName = "Res_ID")
    private int Res_ID;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Sat")
    private int Sat;
    @DatabaseField(columnName = "State_Name")
    private String State_Name;
    @DatabaseField(columnName = "State_No", defaultValue = "0")
    private int State_No = 0;
    @DatabaseField(columnName = "Sun")
    private int Sun;
    @DatabaseField(columnName = "Thu")
    private int Thu;
    @DatabaseField(columnName = "Tue")
    private int Tue;
    @DatabaseField(columnName = "Wed")
    private int Wed;
    @DatabaseField(columnName = "strResId")
    private int strResId;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "ShortState_default.yml";
    }

    public ShortState() {
        super(ShortState.class);
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

    public int getRes_ID() {
        return this.Res_ID;
    }

    public void setRes_ID(int i) {
        this.Res_ID = i;
    }

    public int getAuto_Change() {
        return this.Auto_Change;
    }

    public void setAuto_Change(int i) {
        this.Auto_Change = i;
    }

    public int getMon() {
        return this.Mon;
    }

    public void setMon(int i) {
        this.Mon = i;
    }

    public int getTue() {
        return this.Tue;
    }

    public void setTue(int i) {
        this.Tue = i;
    }

    public int getWed() {
        return this.Wed;
    }

    public void setWed(int i) {
        this.Wed = i;
    }

    public int getThu() {
        return this.Thu;
    }

    public void setThu(int i) {
        this.Thu = i;
    }

    public int getFri() {
        return this.Fri;
    }

    public void setFri(int i) {
        this.Fri = i;
    }

    public int getSat() {
        return this.Sat;
    }

    public void setSat(int i) {
        this.Sat = i;
    }

    public int getSun() {
        return this.Sun;
    }

    public void setSun(int i) {
        this.Sun = i;
    }

    public int getStrResId() {
        return this.strResId;
    }

    public void setStrResId(int i) {
        this.strResId = i;
    }

    public String getCREATE_ID() {
        return this.CREATE_ID;
    }

    public void setCREATE_ID(String str) {
        this.CREATE_ID = str;
    }

    public String getMODIFY_TIME() {
        return this.MODIFY_TIME;
    }

    public void setMODIFY_TIME(String str) {
        this.MODIFY_TIME = str;
    }

    public int getSEND_FLAG() {
        return this.SEND_FLAG;
    }

    public void setSEND_FLAG(int i) {
        this.SEND_FLAG = i;
    }
}
