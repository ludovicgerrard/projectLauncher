package com.zkteco.db;

import com.zkteco.android.io.CSVHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class SQLHelper {
    public static final String PGSQL_CALL_PROCEDURE = "{ call %s }";
    public static final String SQL_CONSTRAINT_MODEL = "CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s (%s) INITIALLY DEFERRED";
    public static final String SQL_CREATE_SCHEMA_MODEL = "CREATE SCHEMA IF NOT EXISTS %s";
    public static final String SQL_CREATE_TABLE_MODEL = "CREATE TABLE IF NOT EXISTS %s (%s)";
    public static final String SQL_DELETE_MODEL = "DELETE FROM %s %s";
    public static final String SQL_INSERT_MODEL = "INSERT INTO %s %s";
    public static final String SQL_PRIMARYKEY_MODEL = "PRIMARY KEY (%s)";
    public static final String SQL_SET_SCHEMA = "SET SCHEMA %s";
    public static final String SQL_UPDATE_MODEL = "UPDATE %s SET %s %s";

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void commitOrRollback(Connection connection, boolean z) throws SQLException {
        if (connection != null) {
            if (z) {
                try {
                    connection.commit();
                } catch (Throwable th) {
                    connection.setAutoCommit(true);
                    throw th;
                }
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);
        }
    }

    public static String escapeSingleQuote(String str) {
        return str.replaceAll("'", "''");
    }

    public static boolean existsSchema(Connection connection, String str) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQLConstants.EXISTS_SCHEMA);
            preparedStatement.setString(1, str);
            return preparedStatement.executeQuery().next();
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public static List<String> getAllDataTables(Connection connection) throws SQLException {
        return getAllDataTables(connection, "public");
    }

    public static List<String> getAllDataTables(Connection connection, String str) throws SQLException {
        ArrayList arrayList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQLConstants.GET_TABLES_FROM_SCHEMA);
            preparedStatement.setString(1, str);
            ResultSet executeQuery = preparedStatement.executeQuery();
            while (executeQuery.next()) {
                String string = executeQuery.getString(1);
                if (!string.matches(SQLConstants.IS_CORE_TABLE)) {
                    arrayList.add(string);
                }
            }
            return arrayList;
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public static List<String> getColumns(Connection connection, String str) throws SQLException {
        ArrayList arrayList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQLConstants.GET_TABLE_COLS);
            preparedStatement.setString(1, str);
            ResultSet executeQuery = preparedStatement.executeQuery();
            while (executeQuery.next()) {
                arrayList.add(executeQuery.getString(1));
            }
            return arrayList;
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public static List<String> getColumns(Connection connection, String str, String str2) throws SQLException {
        ArrayList arrayList = new ArrayList();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQLConstants.GET_SCHEMA_TABLE_COLS);
            preparedStatement.setString(1, str2);
            preparedStatement.setString(2, str);
            ResultSet executeQuery = preparedStatement.executeQuery();
            while (executeQuery.next()) {
                arrayList.add(executeQuery.getString(1));
            }
            return arrayList;
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public static String[] getColumns(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] strArr = new String[columnCount];
        int i = 0;
        while (i < columnCount) {
            int i2 = i + 1;
            strArr[i] = metaData.getColumnName(i2);
            i = i2;
        }
        return strArr;
    }

    public static String getInClause(int i) {
        StringBuilder sb = new StringBuilder(" IN (");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append('?');
            if (i2 < i) {
                sb.append(CSVHelper.SEPARATOR);
            }
        }
        sb.append(") ");
        return sb.toString();
    }

    private static List<String> getNotSelectTables(String str, int i) {
        Matcher matcher;
        if (i == 1) {
            matcher = SQLConstants.INSERT_TABLES.matcher(str);
        } else if (i == 2) {
            matcher = SQLConstants.UPDATE_TABLES.matcher(str);
        } else if (i == 3) {
            matcher = SQLConstants.DELETE_TABLES.matcher(str);
        } else {
            throw new UnsupportedOperationException("Query type not supported: " + str);
        }
        ArrayList arrayList = new ArrayList();
        if (matcher.find()) {
            arrayList.add(matcher.group(1));
        }
        return arrayList;
    }

    public static int getSQLType(String str) {
        String trim = str.trim();
        if (SQLConstants.IS_SELECT.matcher(trim).find()) {
            return 0;
        }
        if (SQLConstants.IS_UPDATE.matcher(trim).find()) {
            return 2;
        }
        if (SQLConstants.IS_INSERT.matcher(trim).find()) {
            return 1;
        }
        if (SQLConstants.IS_DELETE.matcher(trim).find()) {
            return 3;
        }
        if (!SQLConstants.IS_ATTACH.matcher(trim).find()) {
            return -1;
        }
        throw new UnsupportedOperationException("Query type not supported: " + trim);
    }

    public static List<String> getTables(String str) {
        int sQLType = getSQLType(str);
        if (sQLType == 0) {
            throw new UnsupportedOperationException("SELECTs not implemented");
        } else if (sQLType == 1 || sQLType == 2 || sQLType == 3) {
            return getNotSelectTables(str, sQLType);
        } else {
            return null;
        }
    }

    public static void setColsFromMap(StringBuilder sb, Map<String, String> map) {
        StringBuilder sb2 = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            sb2.append((String) it.next().getKey());
            if (it.hasNext()) {
                sb2.append(", ");
            }
        }
        sb.append(sb2);
    }

    public static void setSetFromMap(StringBuilder sb, Map<String, String> map) {
        StringBuilder sb2 = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            if (next.getValue() == null) {
                sb2.append((String) next.getKey()).append(SQLConstants.EQUAL).append((String) next.getValue());
            } else {
                sb2.append((String) next.getKey()).append(SQLConstants.EQUAL).append("'" + escapeSingleQuote((String) next.getValue()) + "'");
            }
            if (it.hasNext()) {
                sb2.append(", ");
            }
        }
        sb.append(sb2);
    }

    public static void setValuesFromMap(StringBuilder sb, Map<String, String> map) {
        StringBuilder sb2 = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            if (next.getValue() == null) {
                sb2.append((String) next.getValue());
            } else {
                sb2.append("'" + escapeSingleQuote((String) next.getValue()) + "'");
            }
            if (it.hasNext()) {
                sb2.append(", ");
            }
        }
        sb.append(sb2);
    }

    public static void setWhereFromMap(StringBuilder sb, Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        StringBuilder sb2 = new StringBuilder();
        if (it.hasNext()) {
            sb2.append(" WHERE ");
            while (it.hasNext()) {
                Map.Entry next = it.next();
                if (next.getValue() == null) {
                    sb2.append((String) next.getKey()).append(SQLConstants.EQUAL).append((String) next.getValue());
                } else {
                    sb2.append((String) next.getKey()).append(SQLConstants.EQUAL).append("'" + escapeSingleQuote((String) next.getValue()) + "'");
                }
                if (it.hasNext()) {
                    sb2.append(" AND ");
                }
            }
        }
        sb.append(sb2);
    }

    public static boolean isCreateTable(String str) {
        return SQLConstants.IS_CREATE_TABLE.matcher(str).find();
    }

    protected SQLHelper() {
    }
}
