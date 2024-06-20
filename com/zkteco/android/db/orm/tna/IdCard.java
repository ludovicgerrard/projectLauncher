package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ID_CARD")
public class IdCard extends AbstractORM<IdCard> implements Serializable {
    private static final long serialVersionUID = 1512784716883L;
    @DatabaseField(columnName = "Additional_Info")
    private String Additional_Info;
    @DatabaseField(columnName = "Address")
    private String Address;
    @DatabaseField(columnName = "Birthday")
    private String Birthday;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "DN_Num")
    private String DN_Num;
    @DatabaseField(columnName = "FP_Template1", dataType = DataType.BYTE_ARRAY)
    private byte[] FP_Template1;
    @DatabaseField(columnName = "FP_Template2", dataType = DataType.BYTE_ARRAY)
    private byte[] FP_Template2;
    @DatabaseField(columnName = "Gender")
    private int Gender;
    @DatabaseField(columnName = "ID_Num")
    private String ID_Num;
    @DatabaseField(columnName = "Issuer")
    private String Issuer;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Name")
    private String Name;
    @DatabaseField(columnName = "Nation")
    private int Nation;
    @DatabaseField(columnName = "Notice")
    private String Notice;
    @DatabaseField(columnName = "Photo", dataType = DataType.BYTE_ARRAY)
    private byte[] Photo;
    @DatabaseField(columnName = "Reserve")
    private String Reserve;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "SN_Num")
    private String SN_Num;
    @DatabaseField(columnName = "User_PIN")
    private String User_PIN;
    @DatabaseField(columnName = "Valid_Info")
    private String Valid_Info;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "IdCard_default.yml";
    }

    public IdCard() {
        super(IdCard.class);
    }

    public String getSN_Num() {
        return this.SN_Num;
    }

    public void setSN_Num(String str) {
        this.SN_Num = str;
    }

    public String getID_Num() {
        return this.ID_Num;
    }

    public void setID_Num(String str) {
        this.ID_Num = str;
    }

    public String getDN_Num() {
        return this.DN_Num;
    }

    public void setDN_Num(String str) {
        this.DN_Num = str;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public int getGender() {
        return this.Gender;
    }

    public void setGender(int i) {
        this.Gender = i;
    }

    public int getNation() {
        return this.Nation;
    }

    public void setNation(int i) {
        this.Nation = i;
    }

    public String getBirthday() {
        return this.Birthday;
    }

    public void setBirthday(String str) {
        this.Birthday = str;
    }

    public String getValid_Info() {
        return this.Valid_Info;
    }

    public void setValid_Info(String str) {
        this.Valid_Info = str;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String str) {
        this.Address = str;
    }

    public String getAdditional_Info() {
        return this.Additional_Info;
    }

    public void setAdditional_Info(String str) {
        this.Additional_Info = str;
    }

    public String getIssuer() {
        return this.Issuer;
    }

    public void setIssuer(String str) {
        this.Issuer = str;
    }

    public byte[] getPhoto() {
        return this.Photo;
    }

    public void setPhoto(byte[] bArr) {
        this.Photo = bArr;
    }

    public byte[] getFP_Template1() {
        return this.FP_Template1;
    }

    public void setFP_Template1(byte[] bArr) {
        this.FP_Template1 = bArr;
    }

    public byte[] getFP_Template2() {
        return this.FP_Template2;
    }

    public void setFP_Template2(byte[] bArr) {
        this.FP_Template2 = bArr;
    }

    public String getReserve() {
        return this.Reserve;
    }

    public void setReserve(String str) {
        this.Reserve = str;
    }

    public String getNotice() {
        return this.Notice;
    }

    public void setNotice(String str) {
        this.Notice = str;
    }

    public String getUser_PIN() {
        return this.User_PIN;
    }

    public void setUser_PIN(String str) {
        this.User_PIN = str;
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
