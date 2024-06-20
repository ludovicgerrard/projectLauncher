package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "LANGUAGE_INFO")
public class LanguageInfo extends AbstractORM<LanguageInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716474L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Language_Flag")
    private int Language_Flag;
    @DatabaseField(columnName = "Language_Name")
    private String Language_Name;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "LanguageInfo_default.yml";
    }

    public LanguageInfo() {
        super(LanguageInfo.class);
    }

    public int getLanguage_Flag() {
        return this.Language_Flag;
    }

    public void setLanguage_Flag(int i) {
        this.Language_Flag = i;
    }

    public String getLanguage_Name() {
        return this.Language_Name;
    }

    public void setLanguage_Name(String str) {
        this.Language_Name = str;
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
