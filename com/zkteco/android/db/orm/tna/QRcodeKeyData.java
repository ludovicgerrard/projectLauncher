package com.zkteco.android.db.orm.tna;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zkteco.android.db.orm.AbstractORM;
import java.io.Serializable;

@DatabaseTable(tableName = "QRCODE_KEY_DATA")
public class QRcodeKeyData extends AbstractORM<QRcodeKeyData> implements Serializable {
    @DatabaseField(columnName = "QRCodeID", defaultValue = "0")
    private int QRCodeID;
    @DatabaseField(columnName = "QRCodeKey")
    private String QRCodeKey;
    @DatabaseField(columnName = "QRCodeType", defaultValue = "0")
    private int QRCodeType;

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    public QRcodeKeyData() {
        super(QRcodeKeyData.class);
    }

    public int getQRCodeID() {
        return this.QRCodeID;
    }

    public void setQRCodeID(int i) {
        this.QRCodeID = i;
    }

    public int getQRCodeType() {
        return this.QRCodeType;
    }

    public void setQRCodeType(int i) {
        this.QRCodeType = i;
    }

    public String getQRCodeKey() {
        return this.QRCodeKey;
    }

    public void setQRCodeKey(String str) {
        this.QRCodeKey = str;
    }

    public String getYAMLFilePathForDefaultValues() {
        return super.getYAMLFilePathForDefaultValues();
    }
}
