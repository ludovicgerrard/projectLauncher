package com.zkteco.db;

import java.util.regex.Pattern;

public class SQLConstants {
    public static final int ATTACH = 4;
    public static final Pattern ATTACH_TABLE = Pattern.compile("attach (.+?) .*$", 2);
    public static final int BEGIN_TRANSACTION = 5;
    public static final int DELETE = 3;
    public static final Pattern DELETE_TABLES = Pattern.compile("delete from (.+?)( where (.*))*$", 2);
    public static final int END_TRANSACTION = 6;
    public static final String EQUAL = " = ";
    public static final String EXISTS_SCHEMA = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?;";
    public static final String GENERIC_SELECT_ALL = "SELECT * FROM %s";
    public static final String GET_SCHEMA_TABLE_COLS = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = ? AND table_schema = ?";
    public static final String GET_TABLES_FROM_SCHEMA = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES where table_schema = ?";
    public static final String GET_TABLE_COLS = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = ?";
    public static final String GET_TABLE_COLS_2 = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '$t'";
    public static final int INSERT = 1;
    public static final Pattern INSERT_TABLES = Pattern.compile("insert into (.+?)[\\( ].*$", 2);
    public static final Pattern IS_ATTACH = Pattern.compile("^attach ", 2);
    public static final String IS_CORE_TABLE = "^core_(.*)";
    static final Pattern IS_CREATE_TABLE = Pattern.compile("^create table ", 2);
    public static final Pattern IS_DELETE = Pattern.compile("^delete ", 2);
    public static final Pattern IS_INSERT = Pattern.compile("^insert ", 2);
    public static final Pattern IS_SELECT = Pattern.compile("^select ", 2);
    public static final Pattern IS_UPDATE = Pattern.compile("^update ", 2);
    public static final int NONE = -1;
    public static final String POSTGRESQL_FK_VIOLATION = "23503";
    public static final String POSTGRESQL_UNIQUE_VIOLATION = "23505";
    public static final int SELECT = 0;
    public static final Pattern SELECT_TABLES = Pattern.compile(" (join|from) (.*?)( *)(?=(\\(|\\)|$| where | left | right | outer | inner | join ))", 2);
    public static final String TABLE_ALIAS = "(?i) +(as )*.+$";
    public static final int UPDATE = 2;
    public static final Pattern UPDATE_TABLES = Pattern.compile("update (.+?) set .+?( where (.*))*$", 2);
}
