package com.zkteco.android.db.orm.util;

import android.os.Build;
import com.google.common.base.Ascii;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String AES = "AES";
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String HEX = "0123456789ABCDEF";
    private static final String SHA1PRNG = "SHA1PRNG";

    public static String encrypt(String str, String str2) {
        try {
            return Base64Encoder.encode(encrypt(str, str2.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateKey() {
        try {
            byte[] bArr = new byte[20];
            SecureRandom.getInstance(SHA1PRNG).nextBytes(bArr);
            return toHex(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getRawKey(byte[] bArr) throws Exception {
        SecureRandom secureRandom;
        KeyGenerator instance = KeyGenerator.getInstance(AES);
        if (Build.VERSION.SDK_INT >= 17) {
            secureRandom = SecureRandom.getInstance(SHA1PRNG, "Crypto");
        } else {
            secureRandom = SecureRandom.getInstance(SHA1PRNG);
        }
        secureRandom.setSeed(bArr);
        instance.init(128, secureRandom);
        return instance.generateKey().getEncoded();
    }

    private static byte[] encrypt(String str, byte[] bArr) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getRawKey(str.getBytes()), AES);
        Cipher instance = Cipher.getInstance(CBC_PKCS5_PADDING);
        instance.init(1, secretKeySpec, new IvParameterSpec(new byte[instance.getBlockSize()]));
        return instance.doFinal(bArr);
    }

    public static String decrypt(String str, String str2) {
        try {
            return new String(decrypt(str, Base64Decoder.decodeToBytes(str2)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decrypt(String str, byte[] bArr) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getRawKey(str.getBytes()), AES);
        Cipher instance = Cipher.getInstance(CBC_PKCS5_PADDING);
        instance.init(2, secretKeySpec, new IvParameterSpec(new byte[instance.getBlockSize()]));
        return instance.doFinal(bArr);
    }

    public static String toHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte appendHex : bArr) {
            appendHex(stringBuffer, appendHex);
        }
        return stringBuffer.toString();
    }

    private static void appendHex(StringBuffer stringBuffer, byte b) {
        stringBuffer.append(HEX.charAt((b >> 4) & 15)).append(HEX.charAt(b & Ascii.SI));
    }
}
