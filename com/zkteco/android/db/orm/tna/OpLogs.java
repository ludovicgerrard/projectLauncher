package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "OP_LOGS")
public class OpLogs extends AbstractORM<OpLogs> implements Serializable {
    private static final long serialVersionUID = 1512784716530L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(canBeNull = false, columnName = "OpTime", defaultValue = "0")
    private String OpTime = "0";
    @DatabaseField(canBeNull = false, columnName = "OpType", defaultValue = "0")
    private int OpType = 0;
    @DatabaseField(columnName = "OpWho", defaultValue = "0")
    private String OpWho = "0";
    @DatabaseField(columnName = "Operator", defaultValue = "0")
    private String Operator = "0";
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Value1", defaultValue = "0")
    private int Value1 = 0;
    @DatabaseField(columnName = "Value2", defaultValue = "0")
    private int Value2 = 0;
    @DatabaseField(columnName = "Value3", defaultValue = "0")
    private int Value3 = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "OpLogs_default.yml";
    }

    public OpLogs() {
        super(OpLogs.class);
    }

    public int getOpType() {
        return this.OpType;
    }

    public void setOpType(int i) {
        this.OpType = i;
    }

    public String getOperator() {
        return this.Operator;
    }

    public void setOperator(String str) {
        this.Operator = str;
    }

    public String getOpTime() {
        return this.OpTime;
    }

    public void setOpTime(String str) {
        this.OpTime = str;
    }

    public String getOpWho() {
        return this.OpWho;
    }

    public void setOpWho(String str) {
        this.OpWho = str;
    }

    public int getValue1() {
        return this.Value1;
    }

    public void setValue1(int i) {
        this.Value1 = i;
    }

    public int getValue2() {
        return this.Value2;
    }

    public void setValue2(int i) {
        this.Value2 = i;
    }

    public int getValue3() {
        return this.Value3;
    }

    public void setValue3(int i) {
        this.Value3 = i;
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
