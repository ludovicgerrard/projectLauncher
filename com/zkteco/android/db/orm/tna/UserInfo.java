package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "USER_INFO")
public class UserInfo extends AbstractORM<UserInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716746L;
    @DatabaseField(columnName = "Acc_Group_ID", defaultValue = "1")
    private int Acc_Group_ID = 1;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Dept_ID", defaultValue = "1")
    private int Dept_ID = 1;
    @DatabaseField(columnName = "EndDatetime", defaultValue = "0")
    private String EndDatetime = "0";
    @DatabaseField(columnName = "Expires", defaultValue = "0")
    private int Expires = 0;
    @DatabaseField(columnName = "Face_Group_ID")
    private int Face_Group_ID;
    @DatabaseField(columnName = "Is_Group_TZ", defaultValue = "1")
    private int Is_Group_TZ = 1;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Main_Card")
    private String Main_Card;
    @DatabaseField(columnName = "Name")
    private String Name;
    @DatabaseField(columnName = "Password")
    private String Password;
    @DatabaseField(columnName = "Privilege")
    private int Privilege;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "StartDatetime", defaultValue = "0")
    private String StartDatetime = "0";
    @DatabaseField(columnName = "Timezone1", defaultValue = "1")
    private int Timezone1 = 1;
    @DatabaseField(columnName = "Timezone2", defaultValue = "0")
    private int Timezone2 = 0;
    @DatabaseField(columnName = "Timezone3", defaultValue = "0")
    private int Timezone3 = 0;
    @DatabaseField(columnName = "User_PIN")
    private String User_PIN;
    @DatabaseField(columnName = "VaildCount", defaultValue = "0")
    private int VaildCount = 0;
    @DatabaseField(columnName = "Verify_Type", defaultValue = "0")
    private int Verify_Type = 0;
    @DatabaseField(columnName = "Vice_Card")
    private String Vice_Card;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "UserInfo_default.yml";
    }

    public UserInfo() {
        super(UserInfo.class);
    }

    public String getUser_PIN() {
        return this.User_PIN;
    }

    public void setUser_PIN(String str) {
        this.User_PIN = str;
    }

    public int getPrivilege() {
        return this.Privilege;
    }

    public void setPrivilege(int i) {
        this.Privilege = i;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String str) {
        this.Password = str;
    }

    public int getFace_Group_ID() {
        return this.Face_Group_ID;
    }

    public void setFace_Group_ID(int i) {
        this.Face_Group_ID = i;
    }

    public int getAcc_Group_ID() {
        return this.Acc_Group_ID;
    }

    public void setAcc_Group_ID(int i) {
        this.Acc_Group_ID = i;
    }

    public int getDept_ID() {
        return this.Dept_ID;
    }

    public void setDept_ID(int i) {
        this.Dept_ID = i;
    }

    public int getIs_Group_TZ() {
        return this.Is_Group_TZ;
    }

    public void setIs_Group_TZ(int i) {
        this.Is_Group_TZ = i;
    }

    public int getVerify_Type() {
        return this.Verify_Type;
    }

    public void setVerify_Type(int i) {
        this.Verify_Type = i;
    }

    public String getMain_Card() {
        return this.Main_Card;
    }

    public void setMain_Card(String str) {
        this.Main_Card = str;
    }

    public String getVice_Card() {
        return this.Vice_Card;
    }

    public void setVice_Card(String str) {
        this.Vice_Card = str;
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

    public int getExpires() {
        return this.Expires;
    }

    public void setExpires(int i) {
        this.Expires = i;
    }

    public String getStartDatetime() {
        return this.StartDatetime;
    }

    public void setStartDatetime(String str) {
        this.StartDatetime = str;
    }

    public String getEndDatetime() {
        return this.EndDatetime;
    }

    public void setEndDatetime(String str) {
        this.EndDatetime = str;
    }

    public int getVaildCount() {
        return this.VaildCount;
    }

    public void setVaildCount(int i) {
        this.VaildCount = i;
    }

    public int getTimezone1() {
        return this.Timezone1;
    }

    public void setTimezone1(int i) {
        this.Timezone1 = i;
    }

    public int getTimezone2() {
        return this.Timezone2;
    }

    public void setTimezone2(int i) {
        this.Timezone2 = i;
    }

    public int getTimezone3() {
        return this.Timezone3;
    }

    public void setTimezone3(int i) {
        this.Timezone3 = i;
    }
}
