package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

import static dao.util.DBUtil.getConnection;

public class UserDAO {

    public static void main(String[] args) {
        System.out.println(new UserDAO().getUser("tom", "123").getId());

    }

    public User getUser(String name, String password) {
        User result = null;
        String sql = "select * from user where name = ? and password = ?";
        try (Connection c = getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = new User();
                result.setId(rs.getInt(1));
                result.setPassword(password);
                result.setName(name);
            }
        } catch (SQLException | NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}