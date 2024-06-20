package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "RES_LIST")
public class ResList extends AbstractORM<ResList> implements Serializable {
    private static final long serialVersionUID = 1512784716625L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Flag", defaultValue = "0")
    private int Flag = 0;
    @DatabaseField(columnName = "Lang_ID")
    private int Lang_ID;
    @DatabaseField(columnName = "Lang_Name")
    private String Lang_Name;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Pic_Name")
    private String Pic_Name;
    @DatabaseField(columnName = "Res_ID")
    private int Res_ID;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Voice_Name")
    private String Voice_Name;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "ResList_default.yml";
    }

    public ResList() {
        super(ResList.class);
    }

    public int getRes_ID() {
        return this.Res_ID;
    }

    public void setRes_ID(int i) {
        this.Res_ID = i;
    }

    public int getLang_ID() {
        return this.Lang_ID;
    }

    public void setLang_ID(int i) {
        this.Lang_ID = i;
    }

    public String getLang_Name() {
        return this.Lang_Name;
    }

    public void setLang_Name(String str) {
        this.Lang_Name = str;
    }

    public String getVoice_Name() {
        return this.Voice_Name;
    }

    public void setVoice_Name(String str) {
        this.Voice_Name = str;
    }

    public String getPic_Name() {
        return this.Pic_Name;
    }

    public void setPic_Name(String str) {
        this.Pic_Name = str;
    }

    public int getFlag() {
        return this.Flag;
    }

    public void setFlag(int i) {
        this.Flag = i;
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
