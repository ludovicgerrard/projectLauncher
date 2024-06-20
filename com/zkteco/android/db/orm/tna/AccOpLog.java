package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_OP_LOG")
public class AccOpLog extends AbstractORM<AccOpLog> implements Serializable {
    private static final long serialVersionUID = 1512784715973L;
    @DatabaseField(columnName = "Admin")
    private int Admin;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "OP")
    private int OP;
    @DatabaseField(columnName = "Objs1")
    private int Objs1;
    @DatabaseField(columnName = "Objs2")
    private int Objs2;
    @DatabaseField(columnName = "Objs3")
    private int Objs3;
    @DatabaseField(columnName = "Objs4")
    private int Objs4;
    @DatabaseField(columnName = "Objs5")
    private int Objs5;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "time_second")
    private int time_second;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccOpLog_default.yml";
    }

    public AccOpLog() {
        super(AccOpLog.class);
    }

    public int getAdmin() {
        return this.Admin;
    }

    public void setAdmin(int i) {
        this.Admin = i;
    }

    public int getOP() {
        return this.OP;
    }

    public void setOP(int i) {
        this.OP = i;
    }

    public int getTime_second() {
        return this.time_second;
    }

    public void setTime_second(int i) {
        this.time_second = i;
    }

    public int getObjs1() {
        return this.Objs1;
    }

    public void setObjs1(int i) {
        this.Objs1 = i;
    }

    public int getObjs2() {
        return this.Objs2;
    }

    public void setObjs2(int i) {
        this.Objs2 = i;
    }

    public int getObjs3() {
        return this.Objs3;
    }

    public void setObjs3(int i) {
        this.Objs3 = i;
    }

    public int getObjs4() {
        return this.Objs4;
    }

    public void setObjs4(int i) {
        this.Objs4 = i;
    }

    public int getObjs5() {
        return this.Objs5;
    }

    public void setObjs5(int i) {
        this.Objs5 = i;
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
