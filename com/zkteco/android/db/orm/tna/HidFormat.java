package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "HID_FORMAT")
public class HidFormat extends AbstractORM<HidFormat> implements Serializable {
    private static final long serialVersionUID = 1512784716416L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(canBeNull = false, columnName = "Card_Bit")
    private int Card_Bit;
    @DatabaseField(columnName = "Card_Format")
    private String Card_Format;
    @DatabaseField(columnName = "First_Even")
    private String First_Even;
    @DatabaseField(columnName = "First_Odd")
    private String First_Odd;
    @DatabaseField(columnName = "Format_Name")
    private String Format_Name;
    @DatabaseField(columnName = "Format_Type")
    private int Format_Type;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Second_Even")
    private String Second_Even;
    @DatabaseField(columnName = "Second_Odd")
    private String Second_Odd;
    @DatabaseField(columnName = "SiteCode")
    private int SiteCode;
    @DatabaseField(columnName = "Status")
    private int Status;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "HidFormat_default.yml";
    }

    public HidFormat() {
        super(HidFormat.class);
    }

    public int getCard_Bit() {
        return this.Card_Bit;
    }

    public void setCard_Bit(int i) {
        this.Card_Bit = i;
    }

    public String getFormat_Name() {
        return this.Format_Name;
    }

    public void setFormat_Name(String str) {
        this.Format_Name = str;
    }

    public String getCard_Format() {
        return this.Card_Format;
    }

    public void setCard_Format(String str) {
        this.Card_Format = str;
    }

    public String getFirst_Even() {
        return this.First_Even;
    }

    public void setFirst_Even(String str) {
        this.First_Even = str;
    }

    public String getSecond_Even() {
        return this.Second_Even;
    }

    public void setSecond_Even(String str) {
        this.Second_Even = str;
    }

    public String getFirst_Odd() {
        return this.First_Odd;
    }

    public void setFirst_Odd(String str) {
        this.First_Odd = str;
    }

    public String getSecond_Odd() {
        return this.Second_Odd;
    }

    public void setSecond_Odd(String str) {
        this.Second_Odd = str;
    }

    public int getFormat_Type() {
        return this.Format_Type;
    }

    public void setFormat_Type(int i) {
        this.Format_Type = i;
    }

    public int getStatus() {
        return this.Status;
    }

    public void setStatus(int i) {
        this.Status = i;
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

    public int getSiteCode() {
        return this.SiteCode;
    }

    public void setSiteCode(int i) {
        this.SiteCode = i;
    }
}
