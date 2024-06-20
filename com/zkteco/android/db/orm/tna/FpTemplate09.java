package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "fptemplate09")
public class FpTemplate09 extends AbstractORM<FpTemplate09> implements Serializable {
    private static final long serialVersionUID = 1512784716358L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "fingerid")
    private int fingerid;
    @DatabaseField(columnName = "pin")
    private int pin;
    @DatabaseField(columnName = "size")
    private int size;
    @DatabaseField(columnName = "template", dataType = DataType.BYTE_ARRAY)
    private byte[] template;
    @DatabaseField(columnName = "valid")
    private int valid;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "FpTemplate09_default.yml";
    }

    public FpTemplate09() {
        super(FpTemplate09.class);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int i) {
        this.pin = i;
    }

    public int getFingerid() {
        return this.fingerid;
    }

    public void setFingerid(int i) {
        this.fingerid = i;
    }

    public int getValid() {
        return this.valid;
    }

    public void setValid(int i) {
        this.valid = i;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public byte[] getTemplate() {
        return this.template;
    }

    public void setTemplate(byte[] bArr) {
        this.template = bArr;
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
