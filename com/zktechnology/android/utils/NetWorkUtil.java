package com.zktechnology.android.utils;

import android.content.Context;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class NetWorkUtil {
    public static final String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public static final int NETWORK_ETHERNET = 1;
    public static final int NETWORK_WLAN = 0;
    private final String TAG = getClass().getSimpleName();
    private Context mContext;

    public NetWorkUtil(Context context) {
        this.mContext = context;
    }

    public static boolean checkIp(String str) {
        return Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(str).matches();
    }

    public static String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    public static String getMaskAddress() {
        String str = "";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.isUp()) {
                    for (InterfaceAddress next : nextElement.getInterfaceAddresses()) {
                        InetAddress address = next.getAddress();
                        if (!address.isLoopbackAddress()) {
                            if (address.getHostAddress().indexOf(":") <= 0) {
                                str = calcMaskByPrefixLength(next.getNetworkPrefixLength());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String calcMaskByPrefixLength(int i) {
        int i2 = -1 << (32 - i);
        int[] iArr = new int[4];
        for (int i3 = 0; i3 < 4; i3++) {
            iArr[3 - i3] = (i2 >> (i3 * 8)) & 255;
        }
        String str = "" + iArr[0];
        for (int i4 = 1; i4 < 4; i4++) {
            str = str + "." + iArr[i4];
        }
        return str;
    }
}
