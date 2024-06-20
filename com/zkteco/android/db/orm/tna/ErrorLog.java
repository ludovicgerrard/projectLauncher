package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ERROR_LOG")
public class ErrorLog extends AbstractORM<ErrorLog> implements Serializable {
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "CmdID")
    private String CmdID;
    @DatabaseField(columnName = "ErrorCode")
    private String ErrorCode;
    @DatabaseField(columnName = "ErrorMsg")
    private String ErrorMsg;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "additional")
    private String additional;
    @DatabaseField(columnName = "dataorigin")
    private String dataorigin;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "ErrorLog_default.yml";
    }

    public ErrorLog() {
        super(ErrorLog.class);
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

    public String getErrorCode() {
        return this.ErrorCode;
    }

    public void setErrorCode(String str) {
        this.ErrorCode = str;
    }

    public String getErrorMsg() {
        return this.ErrorMsg;
    }

    public void setErrorMsg(String str) {
        this.ErrorMsg = str;
    }

    public String getCmdID() {
        return this.CmdID;
    }

    public void setCmdID(String str) {
        this.CmdID = str;
    }

    public String getDataorigin() {
        return this.dataorigin;
    }

    public void setDataorigin(String str) {
        this.dataorigin = str;
    }

    public String getAdditional() {
        return this.additional;
    }

    public void setAdditional(String str) {
        this.additional = str;
    }
}
