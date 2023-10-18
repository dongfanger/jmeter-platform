package org.example;


import org.example.common.enums.DBEnum;
import org.example.common.utils.DBUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtilTest {
    @BeforeClass
    void before() {
        DBUtil.connect(DBEnum.DBX);
    }

    @Test
    void test() throws SQLException {
        ResultSet rs = DBUtil.execute("SELECT * FROM schema.table;");
        assert rs != null;
        while (rs.next()) {
            System.out.println(rs.getString("field"));
        }
    }

    @AfterClass
    void after() {
        DBUtil.close();
    }
}
