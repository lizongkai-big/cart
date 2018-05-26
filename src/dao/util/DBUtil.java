package dao.util;

import annotation.JDBCConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@JDBCConfig(ip="127.0.0.1", database = "cart", encoding = "UTF-8", loginName = "root", password = "password")
public class DBUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public final static Connection getConnection() throws SQLException, NoSuchMethodException, SecurityException{
        JDBCConfig config = DBUtil.class.getAnnotation(JDBCConfig.class);
        String ip = config.ip();
        int port = config.port();
        String database = config.database();
        String encoding = config.encoding();
        String loginName = config.loginName();
        String password = config.password();

        String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s&autoReconnect=true&useSSL=false" +
                "&serverTimezone=UTC", ip, port, database, encoding);
        return DriverManager.getConnection(url, loginName, password);
    }
}
