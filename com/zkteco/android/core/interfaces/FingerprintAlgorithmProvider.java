package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;
import java.util.List;

public class FingerprintAlgorithmProvider extends AbstractProvider implements FingerprintAlgorithmInterface {
    public FingerprintAlgorithmProvider(Provider provider) {
        super(provider);
    }

    public static FingerprintAlgorithmProvider getInstance(Provider provider) {
        return new FingerprintAlgorithmProvider(provider);
    }

    public int init() {
        return ((Integer) getProvider().invoke(FingerprintAlgorithmInterface.INIT, new Object[0])).intValue();
    }

    public void setParameter(int i) {
        getProvider().invoke(FingerprintAlgorithmInterface.PARAMETER, Integer.valueOf(i));
    }

    public String identify(String str, int i) {
        return (String) getProvider().invoke(FingerprintAlgorithmInterface.IDENTIFY, str, Integer.valueOf(i));
    }

    public boolean verify(String str, String str2, int i) {
        return ((Boolean) getProvider().invoke("fp-verify", str, str2, Integer.valueOf(i))).booleanValue();
    }

    public String enroll(String str, List<String> list) {
        return (String) getProvider().invoke(FingerprintAlgorithmInterface.ENROLL, str, list);
    }

    public void save(String str, String str2) {
        getProvider().invoke(FingerprintAlgorithmInterface.SAVE, str, str2);
    }

    public void delete(String str) {
        getProvider().invoke(FingerprintAlgorithmInterface.DELETE, str);
    }

    public void clear() {
        getProvider().invoke(FingerprintAlgorithmInterface.CLEAR, new Object[0]);
    }
}
