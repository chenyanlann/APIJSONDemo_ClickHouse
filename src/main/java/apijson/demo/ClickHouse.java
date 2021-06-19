package apijson.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickHouse {

    public static void main(String[] args) {
        //String createTable = "CREATE TABLE test (FlightDate Date,Year UInt16) ENGINE = MergeTree(FlightDate, (Year, FlightDate), 8192);";//查询数据库
        //String insert = "insert into test (FlightDate,Year) values('2020-06-05',2001);";//查看表
        String select = "select * from sys.apijson_user where id=38710";//查询ontime数据量
        sqlProcess(select);
    }

    public static void sqlProcess(String sql) {
        String address = "jdbc:clickhouse://124.71.139.20:8123";
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        try {
//            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            Class.forName("cc.blynk.clickhouse.ClickHouseDriver");
            connection = DriverManager.getConnection(address, "default", "123456");
            statement = connection.createStatement();
            results = statement.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();
            List<Map> list = new ArrayList();
            while (results.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i), results.getString(rsmd.getColumnName(i)));
                }
                list.add(map);
            }
            for (Map map : list) {
                System.err.println(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关闭连接
            try {
                if (results != null) {
                    results.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

