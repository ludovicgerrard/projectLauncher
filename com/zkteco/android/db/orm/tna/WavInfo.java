package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "WAV_INFO")
public class WavInfo extends AbstractORM<WavInfo> implements Serializable {
    private static final long serialVersionUID = 1512784716774L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Type")
    private int Type;
    @DatabaseField(columnName = "Wav_Name")
    private String Wav_Name;
    @DatabaseField(columnName = "Wav_Path")
    private String Wav_Path;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "WavInfo_default.yml";
    }

    public WavInfo() {
        super(WavInfo.class);
    }

    public String getWav_Name() {
        return this.Wav_Name;
    }

    public void setWav_Name(String str) {
        this.Wav_Name = str;
    }

    public String getWav_Path() {
        return this.Wav_Path;
    }

    public void setWav_Path(String str) {
        this.Wav_Path = str;
    }

    public int getType() {
        return this.Type;
    }

    public void setType(int i) {
        this.Type = i;
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
