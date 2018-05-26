package dao;

import bean.Order;

import java.sql.*;

import static dao.util.DBUtil.getConnection;

public class OrderDAO {
    public OrderDAO() {
    }

    public void insert(Order order) {
        String sql = "insert into order_ values(null, ?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, order.getUser().getId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                int id = rs.getInt(1);
                order.setId(id);
            }
        } catch (SQLException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
