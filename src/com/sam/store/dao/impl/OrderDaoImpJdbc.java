package com.sam.store.dao.impl;

import com.sam.db.core.JdbcTemplate;
import com.sam.store.dao.OrderDao;
import com.sam.store.pojo.Orders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpJdbc implements OrderDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public Orders findById(String id) {

        List<Orders> list = new ArrayList<>();

        String sql = "select id,order_date,status,total from orders where id = ?";

        jdbcTemplate.query(conn ->{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            return ps;
        }, rs -> {
            populate(list, rs);
        });
        if(list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Orders> findAll() {

        List<Orders> list = new ArrayList<>();

        String sql = "select id,order_date,status,total from orders";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps;
        }, rs -> {
            populate(list ,rs);
        });
        return list;
    }

    private void populate(List<Orders> list, ResultSet rs) throws SQLException {
        Orders orders = new Orders();
        orders.setId(rs.getString("id"));
        orders.setOrderDate(new Date(rs.getLong("order_date")));
        orders.setStatus(rs.getInt("status"));
        orders.setTotal(rs.getDouble("total"));

        list.add(orders);
    }

    @Override
    public void create(Orders orders) {

        String sql = "insert into orders(id,order_date,status,total) values(?,?,?,?)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, orders.getId());
            ps.setLong(2, orders.getOrderDate().getTime());
            ps.setInt(3, orders.getStatus());
            ps.setDouble(4, orders.getTotal());

            return ps;
        });
    }

    @Override
    public void modify(Orders orders) {

        String sql = "update orders set order_date=?,status=?,total=? where id=?";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, orders.getOrderDate().getTime());
            ps.setInt(2, orders.getStatus());
            ps.setDouble(3, orders.getTotal());

            ps.setString(4, orders.getId());
            return ps;
        });
    }

    @Override
    public void delete(String id) {

        String sql = "delete from orders where id=?";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            return ps;
        });
    }
}
