package com.zkteco.edk.hardware.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ZkAES256Utils {
    private static final String IV = "XOFUSABCKSVLIFUS";
    private static final String KEY = "XAKSFIXSMGLSIXstiushaxndksjxaist";

    public static String encrypt(String str) {
        return encrypt(KEY, IV, str);
    }

    public static String decrypt(String str) {
        return decrypt(KEY, IV, str);
    }

    public static String encrypt(String str, String str2, String str3) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(str2.getBytes());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            instance.init(1, secretKeySpec, ivParameterSpec);
            return new BASE64Encoder().encode(instance.doFinal(str3.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String str, String str2, String str3) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(str2.getBytes());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            instance.init(2, secretKeySpec, ivParameterSpec);
            return new String(instance.doFinal(new BASE64Decoder().decodeBuffer(str3)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
