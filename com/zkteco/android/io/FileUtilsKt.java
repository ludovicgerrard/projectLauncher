package com.zkteco.android.io;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import com.zkteco.util.YAMLHelper;
import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0007\u001a\n \u0002*\u0004\u0018\u00010\b0\b*\u00020\u0001\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0001\u001a\u001d\u0010\u000b\u001a\n \u0002*\u0004\u0018\u0001H\fH\f\"\u0004\b\u0000\u0010\f*\u00020\u0001¢\u0006\u0002\u0010\r\u001a\u0012\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001\u001a\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0001\u001a\n\u0010\u0012\u001a\u00020\u0011*\u00020\u0001\u001a\n\u0010\u0013\u001a\u00020\u0014*\u00020\u0014\u001a\u0012\u0010\u0015\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0017\u001a\n\u0010\u0018\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0019\u001a\u00020\u0001*\u00020\u0001\u001a\f\u0010\u001a\u001a\u00020\u0001*\u00020\u0001H\u0002\u001a\f\u0010\u001b\u001a\u00020\u0001*\u00020\u0001H\u0002\u001a\n\u0010\u001c\u001a\u00020\u0014*\u00020\u0001\u001a\n\u0010\u001d\u001a\u00020\u001e*\u00020\u0001\"\u0019\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0019\u0010\u0005\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u001f"}, d2 = {"defaultDownloadDirectory", "Ljava/io/File;", "kotlin.jvm.PlatformType", "getDefaultDownloadDirectory", "()Ljava/io/File;", "defaultPhotoDirectory", "getDefaultPhotoDirectory", "asBitmap", "Landroid/graphics/Bitmap;", "asDrawable", "Landroid/graphics/drawable/BitmapDrawable;", "asYamlInstance", "T", "(Ljava/io/File;)Ljava/lang/Object;", "copyToAsSu", "destination", "forceDelete", "", "forceMkdir", "getFileName", "", "installAPK", "context", "Landroid/content/Context;", "installAPKNoPrompt", "installSystemAPK", "installSystemAPKLollipopOrHigher", "installSystemAPKLowerThanLollipop", "md5", "toConfigurationReader", "Lcom/zkteco/android/io/ConfigurationReader;", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: FileUtils.kt */
public final class FileUtilsKt {
    public static final File installAPK(File file, Context context) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setData(Uri.fromFile(file));
        context.startActivity(intent);
        return file;
    }

    public static final File installAPKNoPrompt(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        SystemUtilsKt.runAsSuCommand("pm install -r " + file.getAbsolutePath());
        return file;
    }

    private static final File installSystemAPKLowerThanLollipop(File file) {
        SystemUtilsKt.runAsSuCommand((String) LogUtilsKt.log$default((Object) "busybox mv " + file.getAbsolutePath() + " /system/app/", (String) null, 1, (Object) null));
        return file;
    }

    private static final File installSystemAPKLollipopOrHigher(File file) {
        String str = "/system/priv-app/" + FilesKt.getNameWithoutExtension(file);
        SystemUtilsKt.runAsSuCommand("mkdir " + str);
        SystemUtilsKt.runAsSuCommand("cp " + file.getAbsolutePath() + ' ' + str);
        return file;
    }

    public static final void forceDelete(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        SystemUtilsKt.runAsCommand("rm -r " + file.getCanonicalPath());
    }

    public static final void forceMkdir(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        SystemUtilsKt.runAsCommand("mkdir " + file.getAbsolutePath());
    }

    public static final File installSystemAPK(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        if (Build.VERSION.SDK_INT < 21) {
            return installSystemAPKLowerThanLollipop(file);
        }
        return installSystemAPKLollipopOrHigher(file);
    }

    public static final String md5(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        return StringsKt.substringBefore$default(SystemUtilsKt.runAsCommand("md5 " + file.getAbsolutePath()), ' ', (String) null, 2, (Object) null);
    }

    public static final File copyToAsSu(File file, File file2) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        Intrinsics.checkParameterIsNotNull(file2, "destination");
        SystemUtilsKt.runAsSuCommand("busybox cp " + file.getAbsolutePath() + ' ' + file2.getAbsolutePath());
        return file2;
    }

    public static final ConfigurationReader toConfigurationReader(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        try {
            return new YAMLConfigurationReader(file.getCanonicalPath(), false);
        } catch (Exception e) {
            LogUtilsKt.log(e, "FileUtils.kt");
            return new DefaultConfigurationReader();
        }
    }

    public static final String getFileName(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        String substring = str.substring(StringsKt.lastIndexOf$default((CharSequence) str, File.separatorChar, 0, false, 6, (Object) null) + 1);
        Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
        return substring;
    }

    public static final <T> T asYamlInstance(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        return YAMLHelper.getInstanceFromFile(file.getCanonicalPath());
    }

    public static final File getDefaultDownloadDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public static final File getDefaultPhotoDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    public static final Bitmap asBitmap(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static final BitmapDrawable asDrawable(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$receiver");
        return new BitmapDrawable(asBitmap(file));
    }
}
