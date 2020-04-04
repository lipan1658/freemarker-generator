package utils;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;

/**
 *  数据库连接工具类（仅PG）
 * 〈功能详细描述〉
 *
 * @author lipan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PGDBUtil {

    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;
    static{
        URL = "jdbc:postgresql://xxx.xxx.xxx.xxxx:xx/xxx?currentSchema=xxxx";
        USER = "xxxx";
        PASSWORD = "xxxx";
        DRIVER = "org.postgresql.Driver";
    }

    public static Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection connection= DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }

    public static void close(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
