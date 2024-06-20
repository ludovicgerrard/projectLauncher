package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "MESSAGE_QUEUE")
public class MessageQueue extends AbstractORM<MessageQueue> implements Serializable {
    private static final long serialVersionUID = 1512784716502L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "CREATE_TIME")
    private String CREATE_TIME;
    @DatabaseField(columnName = "ERROR_CODE")
    private int ERROR_CODE;
    @DatabaseField(columnName = "ERROR_MSG")
    private String ERROR_MSG;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "OPERATE")
    private String OPERATE;
    @DatabaseField(columnName = "POST_DATA", dataType = DataType.BYTE_ARRAY)
    private byte[] POST_DATA;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "TABLE_KEY")
    private String TABLE_KEY;
    @DatabaseField(columnName = "TABLE_NAME")
    private String TABLE_NAME;
    @DatabaseField(columnName = "TRAIL_TIMES")
    private int TRAIL_TIMES;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "MessageQueue_default.yml";
    }

    public MessageQueue() {
        super(MessageQueue.class);
    }

    public String getTABLE_NAME() {
        return this.TABLE_NAME;
    }

    public void setTABLE_NAME(String str) {
        this.TABLE_NAME = str;
    }

    public String getTABLE_KEY() {
        return this.TABLE_KEY;
    }

    public void setTABLE_KEY(String str) {
        this.TABLE_KEY = str;
    }

    public String getOPERATE() {
        return this.OPERATE;
    }

    public void setOPERATE(String str) {
        this.OPERATE = str;
    }

    public byte[] getPOST_DATA() {
        return this.POST_DATA;
    }

    public void setPOST_DATA(byte[] bArr) {
        this.POST_DATA = bArr;
    }

    public String getCREATE_TIME() {
        return this.CREATE_TIME;
    }

    public void setCREATE_TIME(String str) {
        this.CREATE_TIME = str;
    }

    public int getERROR_CODE() {
        return this.ERROR_CODE;
    }

    public void setERROR_CODE(int i) {
        this.ERROR_CODE = i;
    }

    public String getERROR_MSG() {
        return this.ERROR_MSG;
    }

    public void setERROR_MSG(String str) {
        this.ERROR_MSG = str;
    }

    public int getTRAIL_TIMES() {
        return this.TRAIL_TIMES;
    }

    public void setTRAIL_TIMES(int i) {
        this.TRAIL_TIMES = i;
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
