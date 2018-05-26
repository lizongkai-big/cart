package dao;

import bean.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.util.DBUtil.getConnection;


public class ProductDAO {
    public int getTotal() {
        int total = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement()){
            String sql = "select count(*) from product";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()){
                total = rs.getInt(1);
            }
            System.out.println("total: " + total);
        }catch (SQLException | NoSuchMethodException e){
            e.printStackTrace();
        }
        return total;
    }

    public void add(Product product){
        String sql = "insert into product values(?, ?, ?)";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, product.getId());
            ps.setString(2, product.getName());
            ps.setFloat(3, product.getPrice());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                product.setId(id);
            }
        }catch (SQLException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    public void delete(int id){
        String sql = "delete product where id = " + id;
        try(Connection c = getConnection(); Statement s = c.createStatement()){
            s.execute(sql);

        }catch (SQLException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    public void update(Product product){
        String sql = "update product set name = ?, price = ? where id = ?";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, product.getName());
            ps.setFloat(2, product.getPrice());
            ps.setInt(3, product.getId());
            ps.execute();
        }catch (SQLException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    public Product getProduct(int id){
        Product product = null;
        String sql = "select * from product where id = " + id;
        try(Connection c = getConnection(); Statement s = c.createStatement()){
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()){
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getFloat("price"));
            }
        }catch (SQLException | NoSuchMethodException e){
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> ListProduct(int start, int count){
        List<Product> products = new ArrayList<>();
        String sql = "select * from product order by id desc limit ?, ?";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getFloat("price"));
                products.add(product);
            }
        }catch (SQLException | NoSuchMethodException e){
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> ListProduct(){
        return ListProduct(0, Short.MAX_VALUE);
    }




}
