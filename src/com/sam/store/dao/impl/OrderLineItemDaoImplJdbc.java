package com.sam.store.dao.impl;

import com.sam.db.core.JdbcTemplate;
import com.sam.store.dao.OrderLineItemDao;
import com.sam.store.pojo.Goods;
import com.sam.store.pojo.OrderLineItem;
import com.sam.store.pojo.Orders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderLineItemDaoImplJdbc implements OrderLineItemDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public OrderLineItem findById(long id) {

        List<OrderLineItem> list = new ArrayList<>();

        String sql = "select id,goodsid,orderid,quantity,sub_total from orderlineitems where id=?";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            return ps;
        }, rs -> {
            populate(list, rs);
        });
        if(list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    private void populate(List<OrderLineItem> list, ResultSet rs) throws SQLException {
        OrderLineItem lineItem= new OrderLineItem();
        lineItem.setId(rs.getLong("id"));
        lineItem.setQuantity(rs.getInt("quantity"));
        lineItem.setSubTotal(rs.getDouble("sub_total"));

        //對應到商品id
        Goods goods = new Goods();
        goods.setId(rs.getLong("goodsid"));
        lineItem.setGoods(goods);

        //對應到訂單id
        Orders orders = new Orders();
        orders.setId(rs.getString("orderid"));
        lineItem.setOrders(orders);

        list.add(lineItem);
    }

    @Override
    public List<OrderLineItem> findAll() {

        List<OrderLineItem> list = new ArrayList<>();

        String sql = "select id,goodsid,orderid,quantity,sub_total from orderlineitems";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);

            return ps;
        }, rs -> {
            populate(list, rs);
        });
        return list;
    }

    @Override
    public void create(OrderLineItem lineItem) {

        String sql = "insert into orderlineitems(id,goodsid,orderid,quantity,sub_total) values(?,?,?,?,?)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, lineItem.getId());
            ps.setLong(2, lineItem.getGoods().getId());
            ps.setString(3, lineItem.getOrders().getId());
            ps.setInt(4, lineItem.getQuantity());
            ps.setDouble(5, lineItem.getSubTotal());

            return ps;
        });
    }

    @Override
    public void modify(OrderLineItem lineItem) {

        String sql = "update orderlineitems set goodsid=?,orderid=?,quantity=?,sub_total=? where id=?";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, lineItem.getGoods().getId());
            ps.setString(2, lineItem.getOrders().getId());
            ps.setInt(3, lineItem.getQuantity());
            ps.setDouble(4, lineItem.getSubTotal());

            ps.setLong(5, lineItem.getId());

            return ps;
        });
    }

    @Override
    public void delete(long id) {

        String sql = "delete from orderlineitems where id=?";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            return ps;
        });
    }
}
