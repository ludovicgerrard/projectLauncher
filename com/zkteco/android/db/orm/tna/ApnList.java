package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "APN_LIST")
public class ApnList extends AbstractORM<ApnList> implements Serializable {
    private static final long serialVersionUID = 1512784716200L;
    @DatabaseField(columnName = "APN")
    private String APN;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Country")
    private String Country;
    @DatabaseField(columnName = "DialNumber")
    private String DialNumber;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Network")
    private String Network;
    @DatabaseField(columnName = "OperatorAlias")
    private String OperatorAlias;
    @DatabaseField(columnName = "Password")
    private String Password;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Username")
    private String Username;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "ApnList_default.yml";
    }

    public ApnList() {
        super(ApnList.class);
    }

    public String getCountry() {
        return this.Country;
    }

    public void setCountry(String str) {
        this.Country = str;
    }

    public String getNetwork() {
        return this.Network;
    }

    public void setNetwork(String str) {
        this.Network = str;
    }

    public String getAPN() {
        return this.APN;
    }

    public void setAPN(String str) {
        this.APN = str;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String str) {
        this.Username = str;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String str) {
        this.Password = str;
    }

    public String getOperatorAlias() {
        return this.OperatorAlias;
    }

    public void setOperatorAlias(String str) {
        this.OperatorAlias = str;
    }

    public String getDialNumber() {
        return this.DialNumber;
    }

    public void setDialNumber(String str) {
        this.DialNumber = str;
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
