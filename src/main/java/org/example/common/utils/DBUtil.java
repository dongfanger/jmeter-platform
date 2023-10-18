package org.example.common.utils;



import com.alibaba.fastjson.JSONObject;
import org.example.common.enums.DBEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class DBUtil {
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    private static Statement stmt = null;

    public static void connect(DBEnum dbEnum) {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(dbEnum.getUrl(), dbEnum.getUsername(), dbEnum.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert conn != null;
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet execute(String sql) {
        try {
            assert stmt != null;
            stmt.execute(sql);
            return stmt.getResultSet();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String json2Sql(Map<String, String[]> map, JSONObject json, String tableName) {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            if (entry.getKey().equals("id")) {
                continue;
            }
            keys.add(entry.getKey());
            String value = entry.getValue() == null ? null : entry.getValue().toString();
            if (Arrays.asList(map.get(tableName)).contains(entry.getKey())) {
                if (value != null) {
                    if (value.indexOf('"') != -1) {
                        value = String.format("\"%s\"", value.replace("\"", "\\\""));
                    } else {
                        value = '"' + value + '"';
                    }
                }
            }
            values.add(value);
        }
        return String.format("insert into %s(%s) values (%s);", tableName, String.join(",", keys), String.join(",", values));
    }
}
