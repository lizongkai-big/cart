package dao;
import bean.OrderItem;

import java.sql.*;

import static dao.util.DBUtil.getConnection;

public class OrderItemDAO {
    public void insert(OrderItem orderItem) {
        String sql = "insert into orderitem values(null, ?, ?, ?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, orderItem.getProduct().getId());
            ps.setInt(2, orderItem.getNum());
            ps.setInt(3, orderItem.getOrder().getId());
            ps.execute();
        }catch (SQLException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }
}
