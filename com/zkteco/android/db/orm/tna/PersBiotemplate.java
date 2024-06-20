package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "Pers_BioTemplate")
public class PersBiotemplate extends AbstractORM<PersBiotemplate> implements Serializable {
    private static final long serialVersionUID = 1512784716816L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "bio_type", defaultValue = "0")
    private int bio_type = 0;
    @DatabaseField(columnName = "create_operator")
    private String create_operator;
    @DatabaseField(columnName = "create_time")
    private String create_time;
    @DatabaseField(columnName = "data_format", defaultValue = "0")
    private int data_format = 0;
    @DatabaseField(columnName = "is_duress", defaultValue = "0")
    private int is_duress = 0;
    @DatabaseField(columnName = "major_ver", defaultValue = "0")
    private int major_ver = 0;
    @DatabaseField(columnName = "minor_ver", defaultValue = "0")
    private int minor_ver = 0;
    @DatabaseField(columnName = "template_id")
    private int template_id;
    @DatabaseField(columnName = "template_no")
    private int template_no;
    @DatabaseField(columnName = "template_no_index")
    private int template_no_index;
    @DatabaseField(canBeNull = false, columnName = "user_pin")
    private String user_pin;
    @DatabaseField(columnName = "valid_flag", defaultValue = "1")
    private int valid_flag = 1;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "PersBiotemplate_default.yml";
    }

    public PersBiotemplate() {
        super(PersBiotemplate.class);
    }

    public String getUser_pin() {
        return this.user_pin;
    }

    public void setUser_pin(String str) {
        this.user_pin = str;
    }

    public String getCreate_operator() {
        return this.create_operator;
    }

    public void setCreate_operator(String str) {
        this.create_operator = str;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(String str) {
        this.create_time = str;
    }

    public int getValid_flag() {
        return this.valid_flag;
    }

    public void setValid_flag(int i) {
        this.valid_flag = i;
    }

    public int getIs_duress() {
        return this.is_duress;
    }

    public void setIs_duress(int i) {
        this.is_duress = i;
    }

    public int getBio_type() {
        return this.bio_type;
    }

    public void setBio_type(int i) {
        this.bio_type = i;
    }

    public int getMajor_ver() {
        return this.major_ver;
    }

    public void setMajor_ver(int i) {
        this.major_ver = i;
    }

    public int getMinor_ver() {
        return this.minor_ver;
    }

    public void setMinor_ver(int i) {
        this.minor_ver = i;
    }

    public int getData_format() {
        return this.data_format;
    }

    public void setData_format(int i) {
        this.data_format = i;
    }

    public int getTemplate_no() {
        return this.template_no;
    }

    public void setTemplate_no(int i) {
        this.template_no = i;
    }

    public int getTemplate_no_index() {
        return this.template_no_index;
    }

    public void setTemplate_no_index(int i) {
        this.template_no_index = i;
    }

    public int getTemplate_id() {
        return this.template_id;
    }

    public void setTemplate_id(int i) {
        this.template_id = i;
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
