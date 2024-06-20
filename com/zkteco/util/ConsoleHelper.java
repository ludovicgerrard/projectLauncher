package com.zkteco.util;

public final class ConsoleHelper {
    public static boolean isRunningInConsole() {
        return System.console() != null;
    }

    private ConsoleHelper() {
    }
}
