package org.example;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.example.common.utils.DBUtil;

import java.util.HashMap;
import java.util.Map;

public class Json2SqlTest {

    public static void main(String[] args) {
        Map<String, String[]> map = new HashMap<String, String[]>() {
            {
                put("table_name", new String[]{"string_field_1", "string_field_2", "string_field_3"});
            }
        };
        JSONObject data = JSON.parseObject("{\n" +
                "                    \"id\":\"500000\",\n" +
                "                    \"tenant_id\":\"1024\",\n" +
                "                    \"business_type\":\"1\",\n" +
                "                    \"state\":\"1\",\n" +
                "                    \"apply_id\":null,\n" +
                "                    \"data_type\":\"1\",\n" +
                "                    \"status\":\"1\",\n" +
                "                    \"created\":\"2022-09-30 10:03:00\",\n" +
                "                    \"modified\":\"2022-10-22 17:50:38.204\",\n" +
                "                    \"data_version\":\"1\",\n" +
                "                    \"ext_map\":null\n" +
                "                }");
        System.out.println(DBUtil.json2Sql(map, data, "table_name"));
    }
}
