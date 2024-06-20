package com.zkteco.android.db.orm;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import java.util.Date;

public abstract class AbstractTimeValidityORM<K> extends AbstractORM<K> {
    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date endValidity;
    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date startValidity;

    public AbstractTimeValidityORM(Class<K> cls) {
        super(cls);
    }
}
