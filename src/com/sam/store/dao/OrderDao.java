package com.sam.store.dao;

import com.sam.store.pojo.Orders;

import java.util.List;

public interface OrderDao {

    Orders findById(String id);

    List<Orders> findAll();

    void create(Orders orders);

    void modify(Orders orders);

    void delete(String id);
}
