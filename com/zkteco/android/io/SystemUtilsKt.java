package com.zkteco.android.io;

import android.content.Context;
import android.os.Build;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\u001a\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u000e\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b\u001a\"\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u001d2\u0014\b\u0002\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001b0\u001f\u001a\u0006\u0010 \u001a\u00020\u0018\u001a\u0010\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u001bH\u0002\u001a\u000e\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u0004\u001a\u001a\u0010%\u001a\u00020\u00182\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001b0\u001f\u001a\u0006\u0010&\u001a\u00020\u0018\u001a\b\u0010'\u001a\u00020\u0018H\u0002\u001a\b\u0010(\u001a\u00020\u0018H\u0002\u001a\u0006\u0010)\u001a\u00020\u0018\u001a\u0006\u0010*\u001a\u00020\u0018\u001a#\u0010+\u001a\u00020\u0018*\u00020,2\u0012\u0010-\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010.\"\u00020\u0001¢\u0006\u0002\u0010/\u001a\n\u00100\u001a\u00020\u0001*\u00020\u0001\u001a\u0012\u00101\u001a\n 2*\u0004\u0018\u00010\u00010\u0001*\u00020\u0001\u001a\n\u00103\u001a\u00020\u0004*\u00020\u0001\u001a\n\u00104\u001a\u00020\u0018*\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001XD¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XD¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001XD¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001XD¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001XD¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001XD¢\u0006\u0002\n\u0000\"$\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8F@FX\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\"\u0011\u0010\u0012\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u0011\u0010\u0015\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014¨\u00065"}, d2 = {"CHECK_ADB_RUNNING", "", "CHECK_TCP_PORT", "DEFAULT_ADB_TCP_PORT", "", "DISABLE_ADB_TCP", "EXTRACT_PROCESS_NUMBER", "Lkotlin/text/Regex;", "SET_ADB_TCP", "START_ADB", "STOP_ADB", "value", "Lcom/zkteco/android/io/ADBState;", "adbState", "getAdbState", "()Lcom/zkteco/android/io/ADBState;", "setAdbState", "(Lcom/zkteco/android/io/ADBState;)V", "androidVersion", "getAndroidVersion", "()Ljava/lang/String;", "hardwareId", "getHardwareId", "backButtonPress", "", "enableNfc", "isEnabled", "", "getProcesses", "", "filter", "Lkotlin/Function1;", "homeButtonPress", "intEnableNfc", "enable", "killProcess", "id", "killProcesses", "launchAndroidLauncher", "launchAndroidLauncher2", "launchAndroidLauncher3", "powerOff", "reboot", "grantPermission", "Landroid/content/Context;", "permissions", "", "(Landroid/content/Context;[Ljava/lang/String;)V", "runAsCommand", "runAsSuCommand", "kotlin.jvm.PlatformType", "runCommand", "sendKeyEvent", "HelpersAndroidIO_release"}, k = 2, mv = {1, 1, 9})
/* compiled from: SystemUtils.kt */
public final class SystemUtilsKt {
    private static final String CHECK_ADB_RUNNING = CHECK_ADB_RUNNING;
    private static final String CHECK_TCP_PORT = CHECK_TCP_PORT;
    private static final int DEFAULT_ADB_TCP_PORT = DEFAULT_ADB_TCP_PORT;
    private static final String DISABLE_ADB_TCP = DISABLE_ADB_TCP;
    private static final Regex EXTRACT_PROCESS_NUMBER = new Regex(".*?\\s+(\\d+).*");
    private static final String SET_ADB_TCP = ("setprop service.adb.tcp.port " + DEFAULT_ADB_TCP_PORT);
    private static final String START_ADB = START_ADB;
    private static final String STOP_ADB = STOP_ADB;

    @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 9})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ADBState.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[ADBState.OFF.ordinal()] = 1;
            iArr[ADBState.USB.ordinal()] = 2;
            iArr[ADBState.TCP.ordinal()] = 3;
        }
    }

    public static final String runAsCommand(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        List<String> execCommandAndRetrieveStringResult = ShellHelper.execCommandAndRetrieveStringResult(str);
        Intrinsics.checkExpressionValueIsNotNull(execCommandAndRetrieveStringResult, "ShellHelper.execCommandA…etrieveStringResult(this)");
        return CollectionsKt.joinToString$default(execCommandAndRetrieveStringResult, "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
    }

    public static final String runAsSuCommand(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        return ShellHelper.execCommandAsSu(str).output;
    }

    public static final int runCommand(String str) {
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        return ShellHelper.execCommands(str);
    }

    public static final void powerOff() {
        runCommand("reboot -p");
    }

    public static final void reboot() {
        runAsSuCommand("reboot");
    }

    public static /* bridge */ /* synthetic */ List getProcesses$default(Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = SystemUtilsKt$getProcesses$1.INSTANCE;
        }
        return getProcesses(function1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0068, code lost:
        r1 = r1.getGroupValues();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.util.List<java.lang.String> getProcesses(kotlin.jvm.functions.Function1<? super java.lang.String, java.lang.Boolean> r7) {
        /*
            java.lang.String r0 = "filter"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r0)
            java.lang.String r0 = "ps"
            java.lang.String r0 = runAsCommand(r0)
            r1 = r0
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            java.lang.String r0 = "\n"
            java.lang.String[] r2 = new java.lang.String[]{r0}
            r3 = 0
            r4 = 0
            r5 = 6
            r6 = 0
            java.util.List r0 = kotlin.text.StringsKt.split$default((java.lang.CharSequence) r1, (java.lang.String[]) r2, (boolean) r3, (int) r4, (int) r5, (java.lang.Object) r6)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Collection r1 = (java.util.Collection) r1
            java.util.Iterator r0 = r0.iterator()
        L_0x0029:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0043
            java.lang.Object r2 = r0.next()
            java.lang.Object r3 = r7.invoke(r2)
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto L_0x0029
            r1.add(r2)
            goto L_0x0029
        L_0x0043:
            java.util.List r1 = (java.util.List) r1
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.Collection r7 = (java.util.Collection) r7
            java.util.Iterator r0 = r1.iterator()
        L_0x0052:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x007d
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            kotlin.text.Regex r2 = EXTRACT_PROCESS_NUMBER
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            kotlin.text.MatchResult r1 = r2.matchEntire(r1)
            if (r1 == 0) goto L_0x0076
            java.util.List r1 = r1.getGroupValues()
            if (r1 == 0) goto L_0x0076
            r2 = 1
            java.lang.Object r1 = r1.get(r2)
            java.lang.String r1 = (java.lang.String) r1
            goto L_0x0077
        L_0x0076:
            r1 = 0
        L_0x0077:
            if (r1 == 0) goto L_0x0052
            r7.add(r1)
            goto L_0x0052
        L_0x007d:
            java.util.List r7 = (java.util.List) r7
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.io.SystemUtilsKt.getProcesses(kotlin.jvm.functions.Function1):java.util.List");
    }

    public static final String killProcess(int i) {
        return runAsCommand("kill " + i);
    }

    public static final void killProcesses(Function1<? super String, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(function1, "filter");
        for (String parseInt : getProcesses(function1)) {
            killProcess(Integer.parseInt(parseInt));
        }
    }

    public static final void grantPermission(Context context, String... strArr) {
        Intrinsics.checkParameterIsNotNull(context, "$receiver");
        Intrinsics.checkParameterIsNotNull(strArr, "permissions");
        if (Build.VERSION.SDK_INT >= 23) {
            for (Object obj : (Object[]) strArr) {
                String str = (String) obj;
                if (context.checkSelfPermission(str) != 0) {
                    runCommand("pm grant " + context.getPackageName() + ' ' + str);
                }
            }
        }
    }

    public static final String getHardwareId() {
        String runAsCommand = runAsCommand("getprop ro.hardware");
        if (runAsCommand != null) {
            return StringsKt.trim((CharSequence) runAsCommand).toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    public static final String getAndroidVersion() {
        String runAsCommand = runAsCommand("getprop ro.build.version.release");
        if (runAsCommand != null) {
            return StringsKt.trim((CharSequence) runAsCommand).toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    public static final void setAdbState(ADBState aDBState) {
        Intrinsics.checkParameterIsNotNull(aDBState, "value");
        int i = WhenMappings.$EnumSwitchMapping$0[aDBState.ordinal()];
        if (i == 1) {
            runCommand(STOP_ADB);
        } else if (i == 2) {
            for (String runCommand : CollectionsKt.listOf(STOP_ADB, DISABLE_ADB_TCP, START_ADB)) {
                runCommand(runCommand);
            }
        } else if (i == 3) {
            for (String runCommand2 : CollectionsKt.listOf(STOP_ADB, SET_ADB_TCP, START_ADB)) {
                runCommand(runCommand2);
            }
        }
    }

    public static final ADBState getAdbState() {
        String runAsCommand = runAsCommand(CHECK_ADB_RUNNING);
        if (runAsCommand == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        } else if (Intrinsics.areEqual((Object) StringsKt.trim((CharSequence) runAsCommand).toString(), (Object) "stopped")) {
            return ADBState.OFF;
        } else {
            String runAsCommand2 = runAsCommand(CHECK_TCP_PORT);
            if (runAsCommand2 != null) {
                String obj = StringsKt.trim((CharSequence) runAsCommand2).toString();
                boolean z = true;
                if (!(obj.length() == 0) && !Intrinsics.areEqual((Object) obj, (Object) "-1")) {
                    z = false;
                }
                if (z) {
                    return ADBState.USB;
                }
                return ADBState.TCP;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
    }

    public static final void sendKeyEvent(int i) {
        runAsCommand("input keyevent " + i);
    }

    public static final void backButtonPress() {
        sendKeyEvent(4);
    }

    public static final void homeButtonPress() {
        sendKeyEvent(3);
    }

    /* access modifiers changed from: private */
    public static final void launchAndroidLauncher3() {
        runAsCommand("am start -n com.android.launcher3/.Launcher");
    }

    /* access modifiers changed from: private */
    public static final void launchAndroidLauncher2() {
        runAsCommand("am start -n com.android.launcher/com.android.launcher2.Launcher");
    }

    public static final void launchAndroidLauncher() {
        ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, SystemUtilsKt$launchAndroidLauncher$1.INSTANCE, 31, (Object) null);
    }

    public static final int enableNfc(boolean z) {
        return runCommand("service call nfc " + intEnableNfc(z));
    }

    private static final int intEnableNfc(boolean z) {
        if (z) {
            return Build.VERSION.SDK_INT >= 24 ? 8 : 7;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            return 7;
        }
        return 6;
    }
}
