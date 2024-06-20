package com.zkteco.android.io;

import android.util.Log;
import com.zkteco.edk.system.lib.base.ZkSystemConstants;
import com.zkteco.util.RegexHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001dB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0002J\u0016\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004J\u0018\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u0018\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u0004J\u000e\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fXD¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fXD¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u000f*\u0004\u0018\u00010\u00040\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/zkteco/android/io/NetworkManagerHelper;", "", "()V", "DHCP", "", "DOWN_COMMAND", "GET_IP", "SET_DNS_COMMAND", "SET_GATEWAY_COMMAND", "SET_STATIC_IP_COMMAND", "STATE", "SUFFIX_1", "", "SUFFIX_2", "TAG", "kotlin.jvm.PlatformType", "UP_COMMAND", "checkNetworkInterface", "", "networkInterface", "dhcp", "tcpIpConfig", "Lcom/zkteco/android/io/NetworkManagerHelper$TcpIpConfig;", "getIp", "setConfiguration", "start", "state", "", "stop", "TcpIpConfig", "HelpersAndroidIO_release"}, k = 1, mv = {1, 1, 9})
/* compiled from: NetworkManagerHelper.kt */
public final class NetworkManagerHelper {
    private static final String DHCP = DHCP;
    private static final String DOWN_COMMAND = DOWN_COMMAND;
    private static final String GET_IP = GET_IP;
    public static final NetworkManagerHelper INSTANCE = new NetworkManagerHelper();
    private static final String SET_DNS_COMMAND = SET_DNS_COMMAND;
    private static final String SET_GATEWAY_COMMAND = SET_GATEWAY_COMMAND;
    private static final String SET_STATIC_IP_COMMAND = SET_STATIC_IP_COMMAND;
    private static final String STATE = STATE;
    private static final int SUFFIX_1 = 1;
    private static final int SUFFIX_2 = 2;
    private static final String TAG = NetworkManagerHelper.class.getName();
    private static final String UP_COMMAND = UP_COMMAND;

    private NetworkManagerHelper() {
    }

    private final void checkNetworkInterface(String str) {
        if (str == null) {
            throw new IllegalArgumentException("No network interface specified");
        }
    }

    public final void start(String str, TcpIpConfig tcpIpConfig) {
        Intrinsics.checkParameterIsNotNull(str, "networkInterface");
        checkNetworkInterface(str);
        try {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(UP_COMMAND, Arrays.copyOf(new Object[]{str}, 1));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            ShellHelper.execCommands(format);
            if (tcpIpConfig != null) {
                setConfiguration(str, tcpIpConfig);
            }
        } catch (IOException e) {
            Throwable th = e;
            Log.e(TAG, Log.getStackTraceString(th));
            throw new RuntimeException(th);
        }
    }

    public final void stop(String str) {
        Intrinsics.checkParameterIsNotNull(str, "networkInterface");
        checkNetworkInterface(str);
        try {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(DOWN_COMMAND, Arrays.copyOf(new Object[]{str}, 1));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            ShellHelper.execCommands(format);
        } catch (IOException e) {
            Throwable th = e;
            Log.e(TAG, Log.getStackTraceString(th));
            throw new RuntimeException(th);
        }
    }

    public final void setConfiguration(String str, TcpIpConfig tcpIpConfig) {
        Intrinsics.checkParameterIsNotNull(str, "networkInterface");
        checkNetworkInterface(str);
        if (tcpIpConfig == null) {
            throw new IllegalArgumentException("No TCP/IP configuration specified");
        } else if (tcpIpConfig.getDhcpEnabled()) {
            Log.i(TAG, "Using DHCP for interface " + str);
        } else {
            try {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format(SET_STATIC_IP_COMMAND, Arrays.copyOf(new Object[]{str, tcpIpConfig.getIpAddress(), tcpIpConfig.getSubnetMask()}, 3));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                String format2 = String.format(SET_GATEWAY_COMMAND, Arrays.copyOf(new Object[]{tcpIpConfig.getGateway(), str}, 2));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
                Locale locale = Locale.getDefault();
                Intrinsics.checkExpressionValueIsNotNull(locale, "Locale.getDefault()");
                String str2 = SET_DNS_COMMAND;
                String format3 = String.format(locale, str2, Arrays.copyOf(new Object[]{Integer.valueOf(SUFFIX_1), tcpIpConfig.getDns1()}, 2));
                Intrinsics.checkExpressionValueIsNotNull(format3, "java.lang.String.format(locale, format, *args)");
                StringCompanionObject stringCompanionObject4 = StringCompanionObject.INSTANCE;
                Locale locale2 = Locale.getDefault();
                Intrinsics.checkExpressionValueIsNotNull(locale2, "Locale.getDefault()");
                String format4 = String.format(locale2, str2, Arrays.copyOf(new Object[]{Integer.valueOf(SUFFIX_2), tcpIpConfig.getDns2()}, 2));
                Intrinsics.checkExpressionValueIsNotNull(format4, "java.lang.String.format(locale, format, *args)");
                ShellHelper.execCommands(format, format3, format4, format2);
            } catch (IOException e) {
                Throwable th = e;
                Log.e(TAG, Log.getStackTraceString(th));
                throw new RuntimeException(th);
            }
        }
    }

    public final boolean state(String str) {
        Intrinsics.checkParameterIsNotNull(str, "networkInterface");
        checkNetworkInterface(str);
        try {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(STATE, Arrays.copyOf(new Object[]{str}, 1));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            if (ShellHelper.execCommandAndRetrieveStringResult(format).size() > 0) {
                return true;
            }
            return false;
        } catch (IOException e) {
            Throwable th = e;
            Log.e(TAG, Log.getStackTraceString(th));
            throw new RuntimeException(th);
        }
    }

    public final void dhcp(String str, TcpIpConfig tcpIpConfig) {
        Intrinsics.checkParameterIsNotNull(str, "networkInterface");
        Intrinsics.checkParameterIsNotNull(tcpIpConfig, "tcpIpConfig");
        checkNetworkInterface(str);
        try {
            tcpIpConfig.setDhcpEnabled(true);
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(DHCP, Arrays.copyOf(new Object[]{str}, 1));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            ShellHelper.execCommands(format);
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public final String getIp(String str) {
        Intrinsics.checkParameterIsNotNull(str, "networkInterface");
        checkNetworkInterface(str);
        try {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(GET_IP, Arrays.copyOf(new Object[]{str}, 1));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            List<String> execCommandAndRetrieveStringResult = ShellHelper.execCommandAndRetrieveStringResult(format);
            if (execCommandAndRetrieveStringResult.size() <= 0) {
                return ZkSystemConstants.NULL_IP_ADDRESS;
            }
            String str2 = execCommandAndRetrieveStringResult.get(0);
            if (!RegexHelper.checkIp(str2)) {
                return ZkSystemConstants.NULL_IP_ADDRESS;
            }
            Intrinsics.checkExpressionValueIsNotNull(str2, "ipToCheck");
            return str2;
        } catch (IOException e) {
            Throwable th = e;
            Log.e(TAG, Log.getStackTraceString(th));
            throw new RuntimeException(th);
        }
    }

    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\b¨\u0006\u001e"}, d2 = {"Lcom/zkteco/android/io/NetworkManagerHelper$TcpIpConfig;", "Ljava/io/Serializable;", "()V", "broadcast", "", "getBroadcast", "()Ljava/lang/String;", "setBroadcast", "(Ljava/lang/String;)V", "dhcpEnabled", "", "getDhcpEnabled", "()Z", "setDhcpEnabled", "(Z)V", "dns1", "getDns1", "setDns1", "dns2", "getDns2", "setDns2", "gateway", "getGateway", "setGateway", "ipAddress", "getIpAddress", "setIpAddress", "subnetMask", "getSubnetMask", "setSubnetMask", "HelpersAndroidIO_release"}, k = 1, mv = {1, 1, 9})
    /* compiled from: NetworkManagerHelper.kt */
    public static final class TcpIpConfig implements Serializable {
        private String broadcast;
        private boolean dhcpEnabled;
        private String dns1;
        private String dns2;
        private String gateway;
        private String ipAddress;
        private String subnetMask;

        public final String getIpAddress() {
            return this.ipAddress;
        }

        public final void setIpAddress(String str) {
            this.ipAddress = str;
        }

        public final String getDns1() {
            return this.dns1;
        }

        public final void setDns1(String str) {
            this.dns1 = str;
        }

        public final String getDns2() {
            return this.dns2;
        }

        public final void setDns2(String str) {
            this.dns2 = str;
        }

        public final String getSubnetMask() {
            return this.subnetMask;
        }

        public final void setSubnetMask(String str) {
            this.subnetMask = str;
        }

        public final String getBroadcast() {
            return this.broadcast;
        }

        public final void setBroadcast(String str) {
            this.broadcast = str;
        }

        public final String getGateway() {
            return this.gateway;
        }

        public final void setGateway(String str) {
            this.gateway = str;
        }

        public final boolean getDhcpEnabled() {
            return this.dhcpEnabled;
        }

        public final void setDhcpEnabled(boolean z) {
            this.dhcpEnabled = z;
        }
    }
}
