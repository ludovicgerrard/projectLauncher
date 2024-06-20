package com.zkteco.android.io;

import android.os.FileObserver;
import android.util.Log;
import com.zkteco.util.YAMLHelper;
import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

public class YAMLConfigurationReader implements ConfigurationReader {
    private static final String CONFIG_YAML_PATH = "/system/config/config.yml";
    private static final String KEY_PUB_PATH = "/system/config/key.pub";
    private static final String SIGNATURE_PATH = "/system/config/signature";
    /* access modifiers changed from: private */
    public static final String TAG = "YAMLConfigurationReader";
    private Map<String, String> configMap;
    /* access modifiers changed from: private */
    public FileObserver configObserver;
    /* access modifiers changed from: private */
    public final String filePath;

    private class YAMLFileObserver extends FileObserver {
        private YAMLFileObserver() {
            super(YAMLConfigurationReader.this.filePath, 2498);
            startWatching();
        }

        public void onEvent(int i, String str) {
            try {
                YAMLConfigurationReader.this.loadConfigs();
            } catch (IOException e) {
                Log.e(YAMLConfigurationReader.TAG, Log.getStackTraceString(e));
            } catch (SignatureException e2) {
                Log.e(YAMLConfigurationReader.TAG, Log.getStackTraceString(e2));
            }
            FileObserver unused = YAMLConfigurationReader.this.configObserver = new YAMLFileObserver();
        }
    }

    public YAMLConfigurationReader() throws IOException, SignatureException {
        this(CONFIG_YAML_PATH, true);
    }

    public YAMLConfigurationReader(String str, boolean z) throws IOException, SignatureException {
        this.configObserver = null;
        this.filePath = str;
        if (z) {
            this.configObserver = new YAMLFileObserver();
        }
        loadConfigs();
        Log.i(TAG, "Configuration loaded: " + this.configMap);
    }

    /* access modifiers changed from: private */
    public void loadConfigs() throws IOException, SignatureException {
        Map<String, String> map = (Map) YAMLHelper.getInstanceFromFile(this.filePath);
        if (map == null) {
            map = new HashMap<>();
        }
        this.configMap = map;
    }

    public Object get(String str, Object obj) {
        String str2 = this.configMap.get(str);
        return str2 == null ? obj : str2;
    }

    public String toString() {
        return "YAMLConfigurationReader{configMap=" + this.configMap + '}';
    }
}
