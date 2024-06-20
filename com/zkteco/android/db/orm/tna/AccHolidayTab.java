package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "ACC_HOLIDAY_TAB")
public class AccHolidayTab extends AbstractORM<AccHolidayTab> implements Serializable {
    private static final long serialVersionUID = 1512784715929L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "HolidayDay")
    private int HolidayDay;
    @DatabaseField(columnName = "HolidayType")
    private int HolidayType;
    @DatabaseField(columnName = "Loop")
    private int Loop;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "AccHolidayTab_default.yml";
    }

    public AccHolidayTab() {
        super(AccHolidayTab.class);
    }

    public int getHolidayDay() {
        return this.HolidayDay;
    }

    public void setHolidayDay(int i) {
        this.HolidayDay = i;
    }

    public int getHolidayType() {
        return this.HolidayType;
    }

    public void setHolidayType(int i) {
        this.HolidayType = i;
    }

    public int getLoop() {
        return this.Loop;
    }

    public void setLoop(int i) {
        this.Loop = i;
    }

    public int getSEND_FLAG() {
        return this.SEND_FLAG;
    }

    public void setSEND_FLAG(int i) {
        this.SEND_FLAG = i;
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
}
