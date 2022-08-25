package com.sam.store.dao.impl;

import com.sam.store.dao.OrderDao;
import com.sam.store.pojo.Orders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoImpJdbcTest {

    OrderDao orderDao;

    @BeforeEach
    void setUp() {
        orderDao = new OrderDaoImpJdbc();
    }

    @AfterEach
    void tearDown() {
        orderDao = null;
    }

    @Test
    void findById() {
        Orders orders = orderDao.findById("100");
        assertNotNull(orders);
        assertEquals("100", orders.getId());
        assertEquals(1026, orders.getOrderDate().getTime());
        assertEquals(1, orders.getStatus());
        assertEquals(10, orders.getTotal());
    }

    @Test
    void findAll() {
        List<Orders> list = orderDao.findAll();

        assertEquals(1, list.size());

        Orders orders = list.get(0);
        assertNotNull(orders);
        assertEquals("100", orders.getId());
        assertEquals(1026, orders.getOrderDate().getTime());
        assertEquals(1, orders.getStatus());
        assertEquals(10, orders.getTotal());
    }

    @Test
    void create() {
        Orders orders = new Orders();
        orders.setId("500");
        orders.setOrderDate(new Date(56)); //第一個數字不可以為0，否則會變成不同的數字
        orders.setStatus(1);
        orders.setTotal(1000);

        orderDao.create(orders);

        Orders orders1 = orderDao.findById("300");
        assertNotNull(orders1);
        assertEquals("300", orders1.getId());
        assertEquals(461, orders1.getOrderDate().getTime());
        assertEquals(1, orders1.getStatus());
        assertEquals(1000, orders1.getTotal());
    }

    @Test
    void modify() {
        Orders orders = new Orders();
        orders.setId("200");
        orders.setOrderDate(new Date(963));
        //有預設給值就不用修改(status)，沒給值一定要修改不然會變成null
        //orders.setStatus(1);
        orders.setTotal(60);
        orderDao.modify(orders);

        Orders orders1 = orderDao.findById("200");
        assertNotNull(orders1);
        assertEquals("200", orders1.getId());
        assertEquals(963, orders1.getOrderDate().getTime());
        assertEquals(1, orders1.getStatus());
        assertEquals(60, orders1.getTotal());
    }

    @Test
    void delete() {

        orderDao.delete("300");

        Orders orders = orderDao.findById("300");
        assertNull(orders);
    }
}