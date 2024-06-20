package com.zktechnology.android.utils;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Util {
    public static byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        Log.d("HRApplication", "keyBytes length is:" + secretKeySpec.getEncoded().length);
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, secretKeySpec, new IvParameterSpec(bArr3));
            return instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d("HRApplication", Log.getStackTraceString(e));
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            Log.d("HRApplication", Log.getStackTraceString(e2));
            return null;
        } catch (InvalidAlgorithmParameterException e3) {
            e3.printStackTrace();
            Log.d("HRApplication", Log.getStackTraceString(e3));
            return null;
        } catch (InvalidKeyException e4) {
            e4.printStackTrace();
            Log.d("HRApplication", Log.getStackTraceString(e4));
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            Log.d("HRApplication", Log.getStackTraceString(e5));
            return null;
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
            Log.d("HRApplication", Log.getStackTraceString(e6));
            return null;
        }
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, new IvParameterSpec(bArr3));
            return instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidAlgorithmParameterException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvalidKeyException e4) {
            e4.printStackTrace();
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
            return null;
        }
    }

    public static void decryptFile(String str, String str2, byte[] bArr, byte[] bArr2) {
        File file = new File(str);
        if (file.exists()) {
            int length = (int) file.length();
            byte[] bArr3 = new byte[length];
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                bufferedInputStream.read(bArr3, 0, length);
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] decrypt = decrypt(bArr3, bArr, bArr2);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
                fileOutputStream.write(decrypt);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void encryptFile(String str, String str2, byte[] bArr, byte[] bArr2) {
        File file = new File(str);
        if (file.exists()) {
            int length = (int) file.length();
            byte[] bArr3 = new byte[length];
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                bufferedInputStream.read(bArr3, 0, length);
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] encrypt = encrypt(bArr3, bArr, bArr2);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
                fileOutputStream.write(encrypt);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
