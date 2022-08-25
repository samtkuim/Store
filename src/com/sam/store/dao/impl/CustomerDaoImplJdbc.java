package com.sam.store.dao.impl;

import com.sam.db.core.JdbcTemplate;
import com.sam.store.dao.CustomerDao;
import com.sam.store.pojo.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDaoImplJdbc implements CustomerDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private void populate(List<Customer> list, ResultSet rs) throws SQLException{
        Customer customer = new Customer();
        customer.setId(rs.getString("id"));
        customer.setName((rs.getString("name")));
        customer.setPassword(rs.getString("password"));
        customer.setAddress(rs.getString("address"));
        customer.setPhone(rs.getString("phone"));
        customer.setBirthday(new Date(rs.getLong("birthday")));//birthday是date類型(new Date裡面放Long類型)

        list.add(customer);
    }

    @Override
    public Customer findById(String id) {

        //把查詢到的結果放到集合中
        List<Customer> list = new ArrayList<>();
        String sql = "select id,name,password,address,phone,birthday from customers where id = ?";

        jdbcTemplate.query(conn -> {//當街口只有一個抽象方法時就可以使用lambda
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);//對應到第一個問號
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
    public List<Customer> findAll() {

        List<Customer> list = new ArrayList<>();
        String sql = "select id, name, password, address, phone, birthday from customers";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps;
        }, rs -> {

            populate(list, rs);

        });

        return list;
    }

    @Override
    public void create(Customer customer) {

        String sql = "insert into customers(id,name,password,address,phone,birthday) values(?,?,?,?,?,?)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            //設置參數
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getAddress());
            ps.setString(5, customer.getPhone());
            ps.setLong(6, customer.getBirthday().getTime());

           return ps;
        });
    }

    @Override
    public void modify(Customer customer) {

        String sql = "update customers set name=?,password=?,address=?,phone=?,birthday=? where id=?";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhone());
            ps.setLong(5, customer.getBirthday().getTime());

            //注意順序
            ps.setString(6, customer.getId());
            return ps;
        });
    }

    @Override
    public void delete(String id) {

        String sql = "delete from customers where id = ?";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            return ps;
        });
    }
}
