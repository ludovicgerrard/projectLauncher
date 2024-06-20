package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_USER_AUTHORIZE")
public class AccUserAuthorize extends AbstractORM<AccUserAuthorize> implements Serializable {
    private static final long serialVersionUID = 1512784716186L;
    @DatabaseField(columnName = "AuthorizeDoor", defaultValue = "1")
    private int AuthorizeDoor = 1;
    @DatabaseField(columnName = "AuthorizeTimezone")
    private int AuthorizeTimezone;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "UserPIN")
    private String UserPIN;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccUserAuthorize_default.yml";
    }

    public AccUserAuthorize() {
        super(AccUserAuthorize.class);
    }

    public String getUserPIN() {
        return this.UserPIN;
    }

    public void setUserPIN(String str) {
        this.UserPIN = str;
    }

    public int getAuthorizeTimezone() {
        return this.AuthorizeTimezone;
    }

    public void setAuthorizeTimezone(int i) {
        this.AuthorizeTimezone = i;
    }

    public int getAuthorizeDoor() {
        return this.AuthorizeDoor;
    }

    public void setAuthorizeDoor(int i) {
        this.AuthorizeDoor = i;
    }

    public int getSEND_FLAG() {
        return this.SEND_FLAG;
    }

    public void setSEND_FLAG(int i) {
        this.SEND_FLAG = i;
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
}
