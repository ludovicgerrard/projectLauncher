package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "Pers_BioTemplateData")
public class PersBiotemplatedata extends AbstractORM<PersBiotemplatedata> implements Serializable {
    private static final long serialVersionUID = 1508756588426L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "template_data", dataType = DataType.BYTE_ARRAY)
    private byte[] template_data;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "PersBiotemplatedata_default.yml";
    }

    public PersBiotemplatedata() {
        super(PersBiotemplatedata.class);
    }

    public byte[] getTemplate_data() {
        return this.template_data;
    }

    public void setTemplate_data(byte[] bArr) {
        this.template_data = bArr;
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
