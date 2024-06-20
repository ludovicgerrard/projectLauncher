package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "BIOPHOTO_INDEX")
public class BiophotoIndex extends AbstractORM<BiophotoIndex> implements Serializable {
    private static final long serialVersionUID = 1534831557113L;
    @DatabaseField(columnName = "Create_ID")
    private String Create_ID;
    @DatabaseField(columnName = "Modify_Time")
    private String Modify_Time;
    @DatabaseField(columnName = "Name")
    private String Name;
    @DatabaseField(columnName = "Photo_Type")
    private int Photo_Type;
    @DatabaseField(columnName = "Send_Flag", defaultValue = "0")
    private int Send_Flag = 0;
    @DatabaseField(columnName = "User_PIN")
    private String User_PIN;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "BiophotoIndex_default.yml";
    }

    public BiophotoIndex() {
        super(BiophotoIndex.class);
    }

    public int getPhoto_Type() {
        return this.Photo_Type;
    }

    public void setPhoto_Type(int i) {
        this.Photo_Type = i;
    }

    public String getUser_PIN() {
        return this.User_PIN;
    }

    public void setUser_PIN(String str) {
        this.User_PIN = str;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public String getCreate_ID() {
        return this.Create_ID;
    }

    public void setCreate_ID(String str) {
        this.Create_ID = str;
    }

    public String getModify_Time() {
        return this.Modify_Time;
    }

    public void setModify_Time(String str) {
        this.Modify_Time = str;
    }

    public int getSend_Flag() {
        return this.Send_Flag;
    }

    public void setSend_Flag(int i) {
        this.Send_Flag = i;
    }
}
