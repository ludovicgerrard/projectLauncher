package com.zkteco.android.db.orm.util;

import android.content.Context;
import android.media.SoundPool;
import android.os.Environment;
import android.util.SparseIntArray;
import java.util.HashMap;
import java.util.Map;

public final class SpeakerHelper {
    private static final String DEFAULT_SOUND_PATH = (Environment.getExternalStorageDirectory().getPath() + "/ZKTeco/");
    private static final String SOUNDS_FOLDER = "sounds/";
    private static final String TAG = "com.zkteco.android.db.orm.util.SpeakerHelper";
    private static Map<String, Integer> externalSounds = new HashMap();
    private static Listener listener = new Listener();
    private static SparseIntArray rawSounds = new SparseIntArray();
    static int soundLoop = 0;
    private static SoundPool soundPool;

    static {
        SoundPool soundPool2 = new SoundPool(8, 3, 0);
        soundPool = soundPool2;
        soundPool2.setOnLoadCompleteListener(listener);
    }

    private SpeakerHelper() {
    }

    private static class Listener implements SoundPool.OnLoadCompleteListener {
        private Listener() {
        }

        public void onLoadComplete(SoundPool soundPool, int i, int i2) {
            soundPool.play(i, 1.0f, 1.0f, 1, SpeakerHelper.soundLoop, 1.0f);
            SpeakerHelper.soundLoop = 0;
        }
    }

    private static String generateAbsolutePath(Context context, String str, boolean z, String str2) {
        StringBuilder sb = new StringBuilder(DEFAULT_SOUND_PATH);
        sb.append(SOUNDS_FOLDER);
        if (z) {
            sb.append(str2);
            sb.append("/");
        }
        sb.append(str);
        return sb.toString();
    }

    private static int loadSound(Context context, int i) {
        Integer valueOf = Integer.valueOf(soundPool.load(context, i, 1));
        rawSounds.put(i, valueOf.intValue());
        return valueOf.intValue();
    }

    private static int loadSound(String str) {
        Integer valueOf = Integer.valueOf(soundPool.load(str, 1));
        externalSounds.put(str, valueOf);
        return valueOf.intValue();
    }

    public static void playSound(Context context, int i) {
        synchronized (soundPool) {
            int i2 = rawSounds.get(i);
            if (i2 == 0) {
                loadSound(context, i);
            }
            soundPool.play(i2, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public static void playSound(Context context, String str, boolean z, String str2) {
        playSoundWithAbsolutePath(generateAbsolutePath(context, str, z, str2), 0);
    }

    public static void playSound(Context context, String str, boolean z, String str2, int i) {
        playSoundWithAbsolutePath(generateAbsolutePath(context, str, z, str2), i);
    }

    public static void playSoundWithAbsolutePath(String str, int i) {
        synchronized (soundPool) {
            Integer num = externalSounds.get(str);
            if (num == null || num.intValue() == 0) {
                soundLoop = i;
                num = Integer.valueOf(loadSound(str));
            }
            soundPool.play(num.intValue(), 1.0f, 1.0f, 1, i, 1.0f);
        }
    }
}
