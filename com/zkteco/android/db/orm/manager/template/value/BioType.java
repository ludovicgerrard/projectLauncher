package com.zkteco.android.db.orm.manager.template.value;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface BioType {
    public static final int BIO_TYPE_AUDIO = 3;
    public static final int BIO_TYPE_COM = 0;
    public static final int BIO_TYPE_FACE = 2;
    public static final int BIO_TYPE_FINGER = 1;
    public static final int BIO_TYPE_IRIS = 4;
    public static final int BIO_TYPE_LIGHT_FACE = 9;
    public static final int BIO_TYPE_PALM = 6;
    public static final int BIO_TYPE_RETINA = 5;
    public static final int BIO_TYPE_VEIN_FINGER = 7;
    public static final int BIO_TYPE_VEIN_PALM = 8;
}
