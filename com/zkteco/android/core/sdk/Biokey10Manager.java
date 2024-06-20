package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.FingerprintAlgorithmInterface;
import com.zkteco.android.core.interfaces.FingerprintAlgorithmProvider;
import com.zkteco.android.core.library.CoreProvider;
import java.util.List;

public class Biokey10Manager implements FingerprintAlgorithmInterface {
    private FingerprintAlgorithmProvider provider;

    public Biokey10Manager(Context context) {
        this.provider = FingerprintAlgorithmProvider.getInstance(new CoreProvider(context));
    }

    public int init() {
        return this.provider.init();
    }

    public void setParameter(int i) {
        this.provider.setParameter(i);
    }

    public String identify(String str, int i) {
        return this.provider.identify(str, i);
    }

    public boolean verify(String str, String str2, int i) {
        return this.provider.verify(str, str2, i);
    }

    public String enroll(String str, List<String> list) {
        return this.provider.enroll(str, list);
    }

    public void save(String str, String str2) {
        this.provider.save(str, str2);
    }

    public void delete(String str) {
        this.provider.delete(str);
    }

    public void clear() {
        this.provider.clear();
    }
}
