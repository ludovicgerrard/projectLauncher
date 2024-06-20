package com.zkteco.android.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/zkteco/android/util/Wallpaper;", "", "wallpaperName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getWallpaperName", "()Ljava/lang/String;", "MAIN", "DASHBOARD", "HelpersAndroidUtils_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: Wallpaper.kt */
public enum Wallpaper {
    MAIN("mainWallpaper"),
    DASHBOARD("dashboardWallpaper");
    
    private final String wallpaperName;

    protected Wallpaper(String str) {
        Intrinsics.checkParameterIsNotNull(str, "wallpaperName");
        this.wallpaperName = str;
    }

    public final String getWallpaperName() {
        return this.wallpaperName;
    }
}
