package com.zkteco.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class DBConnectionHelper {
    private static final String CONNECTION_STRING = "jdbc:%s://%s/%s?user=%s&password=%s";
    public static final String DEFAULT_DB = "postgres";
    public static final int LOG4JDBC_POSTGRESQL = 2;
    private static final String LOG4JDBC_POSTGRESQL_URL_PREFIX = "log4jdbc:postgresql";
    public static final int POSTGRESQL = 1;
    private static final String POSTGRESQL_URL_PREFIX = "postgresql";
    private static final Map<Integer, String> TYPE_MAP;

    static {
        HashMap hashMap = new HashMap();
        TYPE_MAP = hashMap;
        hashMap.put(1, POSTGRESQL_URL_PREFIX);
        hashMap.put(2, LOG4JDBC_POSTGRESQL_URL_PREFIX);
    }

    public static void endConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                processSQLException(e);
            }
        }
    }

    public static Connection getConnection(int i, String str, String str2, String str3) throws SQLException {
        String str4 = TYPE_MAP.get(Integer.valueOf(i));
        if (str4 != null) {
            return DriverManager.getConnection(getConnectionString(str4, str, str2, str3));
        }
        throw new SQLException("Unknown DB type " + i);
    }

    private static String getConnectionString(String str, String str2, String str3, String str4) {
        return String.format(Locale.US, CONNECTION_STRING, new Object[]{str, str2, DEFAULT_DB, str3, str4});
    }

    private static String getConnectionString(String str, String str2, String str3, String str4, String str5) {
        return String.format(Locale.US, CONNECTION_STRING, new Object[]{str, str2, str3, str4, str5});
    }

    public static Connection getConnection(int i, String str, String str2, String str3, String str4) throws SQLException {
        String str5 = TYPE_MAP.get(Integer.valueOf(i));
        if (str5 != null) {
            return DriverManager.getConnection(getConnectionString(str5, str, str2, str3, str4));
        }
        throw new SQLException("Unknown DB type " + i);
    }

    private static void processSQLException(SQLException sQLException) {
        System.out.println("SQLException: " + sQLException.getMessage());
        System.out.println("SQLState: " + sQLException.getSQLState());
        System.out.println("VendorError: " + sQLException.getErrorCode());
    }

    private DBConnectionHelper() {
    }
}
