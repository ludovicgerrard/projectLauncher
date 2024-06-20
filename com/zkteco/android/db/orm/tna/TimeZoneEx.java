package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "TIME_ZONE_EX")
public class TimeZoneEx extends AbstractORM<TimeZoneEx> implements Serializable {
    private static final long serialVersionUID = 1512784716915L;
    @DatabaseField(columnName = "CREATE_ID")
    private String CREATE_ID;
    @DatabaseField(columnName = "Fri_Time1")
    private int Fri_Time1;
    @DatabaseField(columnName = "Fri_Time2")
    private int Fri_Time2;
    @DatabaseField(columnName = "Fri_Time3")
    private int Fri_Time3;
    @DatabaseField(columnName = "Fri_Time4")
    private int Fri_Time4;
    @DatabaseField(columnName = "Fri_Time5")
    private int Fri_Time5;
    @DatabaseField(columnName = "Fri_Time6")
    private int Fri_Time6;
    @DatabaseField(columnName = "Fri_Time7")
    private int Fri_Time7;
    @DatabaseField(columnName = "MODIFY_TIME")
    private String MODIFY_TIME;
    @DatabaseField(columnName = "Mon_Time1")
    private int Mon_Time1;
    @DatabaseField(columnName = "Mon_Time2")
    private int Mon_Time2;
    @DatabaseField(columnName = "Mon_Time3")
    private int Mon_Time3;
    @DatabaseField(columnName = "Mon_Time4")
    private int Mon_Time4;
    @DatabaseField(columnName = "Mon_Time5")
    private int Mon_Time5;
    @DatabaseField(columnName = "Mon_Time6")
    private int Mon_Time6;
    @DatabaseField(columnName = "Mon_Time7")
    private int Mon_Time7;
    @DatabaseField(columnName = "SEND_FLAG", defaultValue = "0")
    private int SEND_FLAG = 0;
    @DatabaseField(columnName = "Sat_Time1")
    private int Sat_Time1;
    @DatabaseField(columnName = "Sat_Time2")
    private int Sat_Time2;
    @DatabaseField(columnName = "Sat_Time3")
    private int Sat_Time3;
    @DatabaseField(columnName = "Sat_Time4")
    private int Sat_Time4;
    @DatabaseField(columnName = "Sat_Time5")
    private int Sat_Time5;
    @DatabaseField(columnName = "Sat_Time6")
    private int Sat_Time6;
    @DatabaseField(columnName = "Sat_Time7")
    private int Sat_Time7;
    @DatabaseField(columnName = "Sun_Time1")
    private int Sun_Time1;
    @DatabaseField(columnName = "Sun_Time2")
    private int Sun_Time2;
    @DatabaseField(columnName = "Sun_Time3")
    private int Sun_Time3;
    @DatabaseField(columnName = "Sun_Time4")
    private int Sun_Time4;
    @DatabaseField(columnName = "Sun_Time5")
    private int Sun_Time5;
    @DatabaseField(columnName = "Sun_Time6")
    private int Sun_Time6;
    @DatabaseField(columnName = "Sun_Time7")
    private int Sun_Time7;
    @DatabaseField(columnName = "Thu_Time1")
    private int Thu_Time1;
    @DatabaseField(columnName = "Thu_Time2")
    private int Thu_Time2;
    @DatabaseField(columnName = "Thu_Time3")
    private int Thu_Time3;
    @DatabaseField(columnName = "Thu_Time4")
    private int Thu_Time4;
    @DatabaseField(columnName = "Thu_Time5")
    private int Thu_Time5;
    @DatabaseField(columnName = "Thu_Time6")
    private int Thu_Time6;
    @DatabaseField(columnName = "Thu_Time7")
    private int Thu_Time7;
    @DatabaseField(columnName = "Timezone_Name")
    private String Timezone_Name;
    @DatabaseField(columnName = "Tue_Time1")
    private int Tue_Time1;
    @DatabaseField(columnName = "Tue_Time2")
    private int Tue_Time2;
    @DatabaseField(columnName = "Tue_Time3")
    private int Tue_Time3;
    @DatabaseField(columnName = "Tue_Time4")
    private int Tue_Time4;
    @DatabaseField(columnName = "Tue_Time5")
    private int Tue_Time5;
    @DatabaseField(columnName = "Tue_Time6")
    private int Tue_Time6;
    @DatabaseField(columnName = "Tue_Time7")
    private int Tue_Time7;
    @DatabaseField(columnName = "Wed_Time1")
    private int Wed_Time1;
    @DatabaseField(columnName = "Wed_Time2")
    private int Wed_Time2;
    @DatabaseField(columnName = "Wed_Time3")
    private int Wed_Time3;
    @DatabaseField(columnName = "Wed_Time4")
    private int Wed_Time4;
    @DatabaseField(columnName = "Wed_Time5")
    private int Wed_Time5;
    @DatabaseField(columnName = "Wed_Time6")
    private int Wed_Time6;
    @DatabaseField(columnName = "Wed_Time7")
    private int Wed_Time7;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public String getYAMLFilePathForDefaultValues() {
        return "TimeZoneEx_default.yml";
    }

    public TimeZoneEx() {
        super(TimeZoneEx.class);
    }

    public String getTimezone_Name() {
        return this.Timezone_Name;
    }

    public void setTimezone_Name(String str) {
        this.Timezone_Name = str;
    }

    public int getMon_Time1() {
        return this.Mon_Time1;
    }

    public void setMon_Time1(int i) {
        this.Mon_Time1 = i;
    }

    public int getMon_Time2() {
        return this.Mon_Time2;
    }

    public void setMon_Time2(int i) {
        this.Mon_Time2 = i;
    }

    public int getMon_Time3() {
        return this.Mon_Time3;
    }

    public void setMon_Time3(int i) {
        this.Mon_Time3 = i;
    }

    public int getMon_Time4() {
        return this.Mon_Time4;
    }

    public void setMon_Time4(int i) {
        this.Mon_Time4 = i;
    }

    public int getMon_Time5() {
        return this.Mon_Time5;
    }

    public void setMon_Time5(int i) {
        this.Mon_Time5 = i;
    }

    public int getMon_Time6() {
        return this.Mon_Time6;
    }

    public void setMon_Time6(int i) {
        this.Mon_Time6 = i;
    }

    public int getMon_Time7() {
        return this.Mon_Time7;
    }

    public void setMon_Time7(int i) {
        this.Mon_Time7 = i;
    }

    public int getTue_Time1() {
        return this.Tue_Time1;
    }

    public void setTue_Time1(int i) {
        this.Tue_Time1 = i;
    }

    public int getTue_Time2() {
        return this.Tue_Time2;
    }

    public void setTue_Time2(int i) {
        this.Tue_Time2 = i;
    }

    public int getTue_Time3() {
        return this.Tue_Time3;
    }

    public void setTue_Time3(int i) {
        this.Tue_Time3 = i;
    }

    public int getTue_Time4() {
        return this.Tue_Time4;
    }

    public void setTue_Time4(int i) {
        this.Tue_Time4 = i;
    }

    public int getTue_Time5() {
        return this.Tue_Time5;
    }

    public void setTue_Time5(int i) {
        this.Tue_Time5 = i;
    }

    public int getTue_Time6() {
        return this.Tue_Time6;
    }

    public void setTue_Time6(int i) {
        this.Tue_Time6 = i;
    }

    public int getTue_Time7() {
        return this.Tue_Time7;
    }

    public void setTue_Time7(int i) {
        this.Tue_Time7 = i;
    }

    public int getWed_Time1() {
        return this.Wed_Time1;
    }

    public void setWed_Time1(int i) {
        this.Wed_Time1 = i;
    }

    public int getWed_Time2() {
        return this.Wed_Time2;
    }

    public void setWed_Time2(int i) {
        this.Wed_Time2 = i;
    }

    public int getWed_Time3() {
        return this.Wed_Time3;
    }

    public void setWed_Time3(int i) {
        this.Wed_Time3 = i;
    }

    public int getWed_Time4() {
        return this.Wed_Time4;
    }

    public void setWed_Time4(int i) {
        this.Wed_Time4 = i;
    }

    public int getWed_Time5() {
        return this.Wed_Time5;
    }

    public void setWed_Time5(int i) {
        this.Wed_Time5 = i;
    }

    public int getWed_Time6() {
        return this.Wed_Time6;
    }

    public void setWed_Time6(int i) {
        this.Wed_Time6 = i;
    }

    public int getWed_Time7() {
        return this.Wed_Time7;
    }

    public void setWed_Time7(int i) {
        this.Wed_Time7 = i;
    }

    public int getThu_Time1() {
        return this.Thu_Time1;
    }

    public void setThu_Time1(int i) {
        this.Thu_Time1 = i;
    }

    public int getThu_Time2() {
        return this.Thu_Time2;
    }

    public void setThu_Time2(int i) {
        this.Thu_Time2 = i;
    }

    public int getThu_Time3() {
        return this.Thu_Time3;
    }

    public void setThu_Time3(int i) {
        this.Thu_Time3 = i;
    }

    public int getThu_Time4() {
        return this.Thu_Time4;
    }

    public void setThu_Time4(int i) {
        this.Thu_Time4 = i;
    }

    public int getThu_Time5() {
        return this.Thu_Time5;
    }

    public void setThu_Time5(int i) {
        this.Thu_Time5 = i;
    }

    public int getThu_Time6() {
        return this.Thu_Time6;
    }

    public void setThu_Time6(int i) {
        this.Thu_Time6 = i;
    }

    public int getThu_Time7() {
        return this.Thu_Time7;
    }

    public void setThu_Time7(int i) {
        this.Thu_Time7 = i;
    }

    public int getFri_Time1() {
        return this.Fri_Time1;
    }

    public void setFri_Time1(int i) {
        this.Fri_Time1 = i;
    }

    public int getFri_Time2() {
        return this.Fri_Time2;
    }

    public void setFri_Time2(int i) {
        this.Fri_Time2 = i;
    }

    public int getFri_Time3() {
        return this.Fri_Time3;
    }

    public void setFri_Time3(int i) {
        this.Fri_Time3 = i;
    }

    public int getFri_Time4() {
        return this.Fri_Time4;
    }

    public void setFri_Time4(int i) {
        this.Fri_Time4 = i;
    }

    public int getFri_Time5() {
        return this.Fri_Time5;
    }

    public void setFri_Time5(int i) {
        this.Fri_Time5 = i;
    }

    public int getFri_Time6() {
        return this.Fri_Time6;
    }

    public void setFri_Time6(int i) {
        this.Fri_Time6 = i;
    }

    public int getFri_Time7() {
        return this.Fri_Time7;
    }

    public void setFri_Time7(int i) {
        this.Fri_Time7 = i;
    }

    public int getSat_Time1() {
        return this.Sat_Time1;
    }

    public void setSat_Time1(int i) {
        this.Sat_Time1 = i;
    }

    public int getSat_Time2() {
        return this.Sat_Time2;
    }

    public void setSat_Time2(int i) {
        this.Sat_Time2 = i;
    }

    public int getSat_Time3() {
        return this.Sat_Time3;
    }

    public void setSat_Time3(int i) {
        this.Sat_Time3 = i;
    }

    public int getSat_Time4() {
        return this.Sat_Time4;
    }

    public void setSat_Time4(int i) {
        this.Sat_Time4 = i;
    }

    public int getSat_Time5() {
        return this.Sat_Time5;
    }

    public void setSat_Time5(int i) {
        this.Sat_Time5 = i;
    }

    public int getSat_Time6() {
        return this.Sat_Time6;
    }

    public void setSat_Time6(int i) {
        this.Sat_Time6 = i;
    }

    public int getSat_Time7() {
        return this.Sat_Time7;
    }

    public void setSat_Time7(int i) {
        this.Sat_Time7 = i;
    }

    public int getSun_Time1() {
        return this.Sun_Time1;
    }

    public void setSun_Time1(int i) {
        this.Sun_Time1 = i;
    }

    public int getSun_Time2() {
        return this.Sun_Time2;
    }

    public void setSun_Time2(int i) {
        this.Sun_Time2 = i;
    }

    public int getSun_Time3() {
        return this.Sun_Time3;
    }

    public void setSun_Time3(int i) {
        this.Sun_Time3 = i;
    }

    public int getSun_Time4() {
        return this.Sun_Time4;
    }

    public void setSun_Time4(int i) {
        this.Sun_Time4 = i;
    }

    public int getSun_Time5() {
        return this.Sun_Time5;
    }

    public void setSun_Time5(int i) {
        this.Sun_Time5 = i;
    }

    public int getSun_Time6() {
        return this.Sun_Time6;
    }

    public void setSun_Time6(int i) {
        this.Sun_Time6 = i;
    }

    public int getSun_Time7() {
        return this.Sun_Time7;
    }

    public void setSun_Time7(int i) {
        this.Sun_Time7 = i;
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
