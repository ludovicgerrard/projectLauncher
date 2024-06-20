package com.zkteco.db;

import com.zkteco.util.FileHelper;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleSQLiteManager {
    private static Object DBLOCK = new Object();
    private static Connection conn;

    private static String getEmptyDBFile() {
        return "/empty_db";
    }

    public static void close() {
        synchronized (DBLOCK) {
            Connection connection = conn;
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    try {
                        e.printStackTrace();
                    } catch (Throwable th) {
                        conn = null;
                        throw th;
                    }
                }
                conn = null;
            }
        }
    }

    public static ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    public static ResultSet executeQuery(String str) throws SQLException {
        return executeQuery(getPreparedStatement(str));
    }

    public static void executeUpdate(PreparedStatement preparedStatement) throws SQLException {
        try {
            preparedStatement.executeUpdate();
        } finally {
            preparedStatement.close();
        }
    }

    public static void executeUpdate(String str) throws SQLException {
        executeUpdate(getPreparedStatement(str));
    }

    public static PreparedStatement getPreparedStatement(String str) throws SQLException {
        return getConnection().prepareStatement(str);
    }

    private static Connection getConnection() throws SQLException {
        Connection connection;
        synchronized (DBLOCK) {
            try {
                if (conn == null) {
                    initialize();
                }
                connection = conn;
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            } catch (IOException e2) {
                throw new SQLException(e2);
            } catch (Throwable th) {
                throw th;
            }
        }
        return connection;
    }

    private static boolean createDB() throws IOException {
        return FileHelper.copyFromJar(getEmptyDBFile(), getCurrentDBFile());
    }

    private static File getCurrentDBFile() {
        return new File("db");
    }

    private static void initialize() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.sqlite.JDBC");
        if (!getCurrentDBFile().exists()) {
            createDB();
        }
        conn = DriverManager.getConnection("jdbc:sqlite:db");
    }
}
