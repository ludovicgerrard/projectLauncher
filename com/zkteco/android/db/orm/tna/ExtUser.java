package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ExtUser")
public class ExtUser extends AbstractORM<ExtUser> implements Serializable {
    private static final long serialVersionUID = 8918434394413825988L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "FirstName")
    private String FirstName;
    @DatabaseField(columnName = "FunSwitch", defaultValue = "0")
    private int FunSwitch;
    @DatabaseField(columnName = "LastName")
    private String LastName;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "PersonalVS", defaultValue = "255")
    private int PersonalVS;
    @DatabaseField(columnName = "Pin")
    private String Pin;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "ExtUser_default.yml";
    }

    public ExtUser() {
        super(ExtUser.class);
    }

    public String getPin() {
        return this.Pin;
    }

    public void setPin(String str) {
        this.Pin = str;
    }

    public int getFunSwitch() {
        return this.FunSwitch;
    }

    public void setFunSwitch(int i) {
        this.FunSwitch = i;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public void setFirstName(String str) {
        this.FirstName = str;
    }

    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String str) {
        this.LastName = str;
    }

    public int getPersonalVS() {
        return this.PersonalVS;
    }

    public void setPersonalVS(int i) {
        this.PersonalVS = i;
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
