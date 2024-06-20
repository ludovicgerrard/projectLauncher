package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "LARGE_MQ")
public class LargeMq extends AbstractORM<LargeMq> implements Serializable {
    private static final long serialVersionUID = 1512784716488L;
    @DatabaseField(columnName = "ATTACH_INFO")
    private String ATTACH_INFO;
    @DatabaseField(columnName = "CMD_DESC")
    private String CMD_DESC;
    @DatabaseField(columnName = "CMD_ID")
    private String CMD_ID;
    @DatabaseField(columnName = "CONDITION", defaultValue = "1")
    private String CONDITION = "1";
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "END_KEY", defaultValue = "0")
    private int END_KEY = 0;
    @DatabaseField(columnName = "FIELD_LIST")
    private String FIELD_LIST;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "PACK_SIZE")
    private int PACK_SIZE;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "SEND_KEY", defaultValue = "0")
    private int SEND_KEY = 0;
    @DatabaseField(columnName = "START_KEY", defaultValue = "0")
    private int START_KEY = 0;
    @DatabaseField(columnName = "TABLE_NAME")
    private String TABLE_NAME;
    @DatabaseField(columnName = "TYPE", defaultValue = "0")
    private int TYPE = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "LargeMq_default.yml";
    }

    public LargeMq() {
        super(LargeMq.class);
    }

    public String getTABLE_NAME() {
        return this.TABLE_NAME;
    }

    public void setTABLE_NAME(String str) {
        this.TABLE_NAME = str;
    }

    public int getSTART_KEY() {
        return this.START_KEY;
    }

    public void setSTART_KEY(int i) {
        this.START_KEY = i;
    }

    public int getEND_KEY() {
        return this.END_KEY;
    }

    public void setEND_KEY(int i) {
        this.END_KEY = i;
    }

    public int getSEND_KEY() {
        return this.SEND_KEY;
    }

    public void setSEND_KEY(int i) {
        this.SEND_KEY = i;
    }

    public String getCONDITION() {
        return this.CONDITION;
    }

    public void setCONDITION(String str) {
        this.CONDITION = str;
    }

    public int getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(int i) {
        this.TYPE = i;
    }

    public String getFIELD_LIST() {
        return this.FIELD_LIST;
    }

    public void setFIELD_LIST(String str) {
        this.FIELD_LIST = str;
    }

    public int getPACK_SIZE() {
        return this.PACK_SIZE;
    }

    public void setPACK_SIZE(int i) {
        this.PACK_SIZE = i;
    }

    public String getCMD_ID() {
        return this.CMD_ID;
    }

    public void setCMD_ID(String str) {
        this.CMD_ID = str;
    }

    public String getCMD_DESC() {
        return this.CMD_DESC;
    }

    public void setCMD_DESC(String str) {
        this.CMD_DESC = str;
    }

    public String getATTACH_INFO() {
        return this.ATTACH_INFO;
    }

    public void setATTACH_INFO(String str) {
        this.ATTACH_INFO = str;
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
