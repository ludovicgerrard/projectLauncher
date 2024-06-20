package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "WIFI_INFO")
public class WifiInfo extends AbstractORM<WifiInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716788L;
    @DatabaseField(columnName = "Auth_Key")
    private String Auth_Key;
    @DatabaseField(columnName = "Auth_Type")
    private int Auth_Type;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Connect_Time")
    private String Connect_Time;
    @DatabaseField(columnName = "ESSID")
    private String ESSID;
    @DatabaseField(columnName = "MAC")
    private String MAC;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "WifiInfo_default.yml";
    }

    public WifiInfo() {
        super(WifiInfo.class);
    }

    public String getMAC() {
        return this.MAC;
    }

    public void setMAC(String str) {
        this.MAC = str;
    }

    public String getESSID() {
        return this.ESSID;
    }

    public void setESSID(String str) {
        this.ESSID = str;
    }

    public int getAuth_Type() {
        return this.Auth_Type;
    }

    public void setAuth_Type(int i) {
        this.Auth_Type = i;
    }

    public String getAuth_Key() {
        return this.Auth_Key;
    }

    public void setAuth_Key(String str) {
        this.Auth_Key = str;
    }

    public String getConnect_Time() {
        return this.Connect_Time;
    }

    public void setConnect_Time(String str) {
        this.Connect_Time = str;
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
