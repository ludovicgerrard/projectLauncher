package com.zkteco.edk.system.lib.utils;

import android.text.TextUtils;
import android.util.Log;
import com.zkteco.edk.system.lib.bean.ZkEthernetConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class ZkNetWorkUtils {
    private static final String COMMAND = "ifconfig";
    private static final String DEFAULT_IP = "0.0.0.0";
    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
    public static final String IP_ADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    public static String getDefaultIp() {
        return "0.0.0.0";
    }

    public static boolean checkIp(String str) {
        return Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(str).matches();
    }

    public static String getEthIPv4FromIfconfig() {
        return getIPFromIfconfig("eth0");
    }

    public static String getWifiIPv4FromIfconfig() {
        return getIPFromIfconfig("wlan0");
    }

    private static String getIPFromIfconfig(String str) {
        String str2 = ZkShellUtils.execCommand("ifconfig " + str, false).successMsg;
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        boolean z = false;
        for (String str3 : str2.split(" ")) {
            if (z) {
                if (str3.contains("addr:")) {
                    return str3.replaceAll("addr:", "");
                }
            } else if ("inet".equals(str3)) {
                z = true;
            }
        }
        return "";
    }

    public static String getWlan0Mask() {
        String str;
        IOException e;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{COMMAND}).getInputStream()));
            str = "0.0.0.0";
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (readLine.startsWith("wlan0     ")) {
                            str = "0.0.0.0";
                        }
                        if (!getLineMask(readLine).equals("")) {
                            str = getLineMask(readLine);
                        }
                        if (readLine.startsWith("p2p0      ")) {
                            break;
                        }
                    } else {
                        break;
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    return str;
                }
            }
        } catch (IOException e3) {
            IOException iOException = e3;
            str = "0.0.0.0";
            e = iOException;
            e.printStackTrace();
            return str;
        }
        return str;
    }

    public static String getEth0Mask() {
        String str;
        IOException e;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{COMMAND}).getInputStream()));
            str = "0.0.0.0";
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (readLine.startsWith("eth0      ")) {
                            str = "0.0.0.0";
                        }
                        if (!getLineMask(readLine).equals("")) {
                            str = getLineMask(readLine);
                        }
                        if (readLine.startsWith("lo        ")) {
                            break;
                        }
                    } else {
                        break;
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    return str;
                }
            }
        } catch (IOException e3) {
            IOException iOException = e3;
            str = "0.0.0.0";
            e = iOException;
            e.printStackTrace();
            return str;
        }
        return str;
    }

    private static String getLineMask(String str) {
        String str2 = "";
        if (str.trim().matches("inet addr:(\\d{1,3}\\.){3}\\d{1,3}( ){2}(Bcast:(\\d{1,3}\\.){3}\\d{1,3}( ){2}){0,1}Mask:(\\d{1,3}\\.){3}\\d{1,3}")) {
            for (String str3 : str.trim().split("( ){2}")) {
                if (str3.length() != 0) {
                    String[] split = str3.split(":");
                    if (split[0].startsWith("inet addr")) {
                        Log.d("mask", "----ipAddr: " + split[1]);
                    } else if (split[0].startsWith("Bcast")) {
                        Log.d("mask", "----Bcast: " + split[1]);
                    } else if (split[0].startsWith("Mask")) {
                        Log.d("mask", "----mask: " + split[1]);
                        str2 = split[1];
                    }
                }
            }
        }
        return str2;
    }

    public static boolean isValidIPV4ByJDK(String str) {
        try {
            return Inet4Address.getByName(str).getHostAddress().equals(str);
        } catch (UnknownHostException unused) {
            return false;
        }
    }

    public static boolean isValidIPV4ByCustomRegex(String str) {
        if (str == null || str.trim().isEmpty() || !IPv4_PATTERN.matcher(str).matches()) {
            return false;
        }
        try {
            for (String str2 : str.split("\\.")) {
                if (Integer.parseInt(str2) > 255 || (str2.length() > 1 && str2.startsWith("0"))) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static boolean changeErrorSpEthConfig(ZkEthernetConfig zkEthernetConfig, ZkEthernetConfig zkEthernetConfig2) {
        boolean z;
        if (TextUtils.isEmpty(zkEthernetConfig.getIpAddress()) || zkEthernetConfig.getIpAddress().equals(zkEthernetConfig2.getIpAddress())) {
            z = false;
        } else {
            zkEthernetConfig2.setIpAddress(zkEthernetConfig.getIpAddress());
            z = true;
        }
        if (!TextUtils.isEmpty(zkEthernetConfig.getDns1()) && !zkEthernetConfig.getDns1().equals(zkEthernetConfig2.getDns1())) {
            zkEthernetConfig2.setDns1(zkEthernetConfig.getDns1());
            z = true;
        }
        if (!TextUtils.isEmpty(zkEthernetConfig.getDns2()) && !zkEthernetConfig.getDns2().equals(zkEthernetConfig2.getDns2())) {
            zkEthernetConfig2.setDns2(zkEthernetConfig.getDns2());
            z = true;
        }
        if (!TextUtils.isEmpty(zkEthernetConfig.getGateway()) && !zkEthernetConfig.getGateway().equals(zkEthernetConfig2.getGateway())) {
            zkEthernetConfig2.setGateway(zkEthernetConfig.getGateway());
            z = true;
        }
        if (TextUtils.isEmpty(zkEthernetConfig.getSubnetMask()) || zkEthernetConfig.getSubnetMask().equals(zkEthernetConfig2.getSubnetMask())) {
            return z;
        }
        zkEthernetConfig2.setSubnetMask(zkEthernetConfig.getSubnetMask());
        return true;
    }
}
