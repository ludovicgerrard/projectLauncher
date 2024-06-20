package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "CLASS_INFO")
public class ClassInfo extends AbstractORM<ClassInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716286L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Class_Name")
    private String Class_Name;
    @DatabaseField(columnName = "Class_No")
    private int Class_No;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "OvertimeEnd")
    private int OvertimeEnd;
    @DatabaseField(columnName = "OvertimeStart")
    private int OvertimeStart;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "TimeEnd1")
    private int TimeEnd1;
    @DatabaseField(columnName = "TimeEnd2")
    private int TimeEnd2;
    @DatabaseField(columnName = "TimeStart1")
    private int TimeStart1;
    @DatabaseField(columnName = "TimeStart2")
    private int TimeStart2;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "ClassInfo_default.yml";
    }

    public ClassInfo() {
        super(ClassInfo.class);
    }

    public int getClass_No() {
        return this.Class_No;
    }

    public void setClass_No(int i) {
        this.Class_No = i;
    }

    public String getClass_Name() {
        return this.Class_Name;
    }

    public void setClass_Name(String str) {
        this.Class_Name = str;
    }

    public int getTimeStart1() {
        return this.TimeStart1;
    }

    public void setTimeStart1(int i) {
        this.TimeStart1 = i;
    }

    public int getTimeEnd1() {
        return this.TimeEnd1;
    }

    public void setTimeEnd1(int i) {
        this.TimeEnd1 = i;
    }

    public int getTimeStart2() {
        return this.TimeStart2;
    }

    public void setTimeStart2(int i) {
        this.TimeStart2 = i;
    }

    public int getTimeEnd2() {
        return this.TimeEnd2;
    }

    public void setTimeEnd2(int i) {
        this.TimeEnd2 = i;
    }

    public int getOvertimeStart() {
        return this.OvertimeStart;
    }

    public void setOvertimeStart(int i) {
        this.OvertimeStart = i;
    }

    public int getOvertimeEnd() {
        return this.OvertimeEnd;
    }

    public void setOvertimeEnd(int i) {
        this.OvertimeEnd = i;
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
