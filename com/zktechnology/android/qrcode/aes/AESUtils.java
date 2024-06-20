package com.zktechnology.android.qrcode.aes;

import android.util.Log;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESUtils {
    private static final String KEY_ALGORITHM = "AES";
    private static final String TAG = "AESUtils";
    final String algorithmStr = "AES/CBC/PKCS7Padding";
    private Cipher cipher;
    private Key key;

    public byte[] decrypt(byte[] bArr, String str) {
        byte[] bytes = str.getBytes();
        byte[] bytes2 = str.substring(0, 16).getBytes();
        init(bytes);
        try {
            this.cipher.init(2, this.key, new IvParameterSpec(bytes2));
            return this.cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getmKey() {
        int zeroTimeZoneDate = getZeroTimeZoneDate();
        String upperCase = getMD5Str(getMD5Str(String.valueOf(zeroTimeZoneDate)).toUpperCase() + (zeroTimeZoneDate + 42)).toUpperCase();
        Log.d("qrcode", "md5: " + upperCase + 10);
        Log.d("qrcode", "IV: " + upperCase.substring(0, 8) + 10);
        return upperCase;
    }

    public int getZeroTimeZoneDate() {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT-0:00"));
        return instance.get(1) * (instance.get(2) + 1) * instance.get(5);
    }

    public static String getMD5Str(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                byte b2 = b & 255;
                if (b2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b2));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void init(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length % 16 != 0) {
                byte[] bArr2 = new byte[(((bArr.length / 16) + (bArr.length % 16 != 0 ? 1 : 0)) * 16)];
                Arrays.fill(bArr2, (byte) 0);
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                bArr = bArr2;
            }
            Security.addProvider(new BouncyCastleProvider());
            this.key = new SecretKeySpec(bArr, KEY_ALGORITHM);
            try {
                this.cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e2) {
                e2.printStackTrace();
            } catch (NoSuchProviderException e3) {
                e3.printStackTrace();
            }
        }
    }
}
