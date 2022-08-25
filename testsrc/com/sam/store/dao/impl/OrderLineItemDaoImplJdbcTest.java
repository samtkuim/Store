package com.sam.store.dao.impl;

import com.sam.store.dao.OrderLineItemDao;
import com.sam.store.pojo.Goods;
import com.sam.store.pojo.OrderLineItem;
import com.sam.store.pojo.Orders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineItemDaoImplJdbcTest {

    OrderLineItemDao lineItemDao;

    @BeforeEach
    void setUp() {
        lineItemDao = new OrderLineItemDaoImplJdbc();
    }

    @AfterEach
    void tearDown() {
        lineItemDao = null;
    }

    @Test
    void findById() {
        OrderLineItem lineItem = lineItemDao.findById(1);
        assertNotNull(lineItem);
        assertEquals(1, lineItem.getId());
        assertEquals(1, lineItem.getGoods().getId());
        assertEquals("200", lineItem.getOrders().getId());
        assertEquals(20, lineItem.getQuantity());
        assertEquals(3000, lineItem.getSubTotal());
    }

    @Test
    void findAll() {
        List<OrderLineItem> list = lineItemDao.findAll();
        assertEquals(1, list.size());

        OrderLineItem lineItem = list.get(0);
        assertNotNull(lineItem);
        assertEquals(1, lineItem.getId());
        assertEquals(1, lineItem.getGoods().getId());
        assertEquals("200", lineItem.getOrders().getId());
        assertEquals(20, lineItem.getQuantity());
        assertEquals(3000, lineItem.getSubTotal());
    }

    @Test
    void create() {
        OrderLineItem lineItem = new OrderLineItem();
        //lineItem.setId(2); //ID為AI
        lineItem.setQuantity(30);
        lineItem.setSubTotal(6000);

        Goods goods = new Goods();
        goods.setId(2);
        lineItem.setGoods(goods);

        Orders orders = new Orders();
        orders.setId("400");
        lineItem.setOrders(orders);

        lineItemDao.create(lineItem);

        OrderLineItem lineItem1 = lineItemDao.findById(2);
        assertNotNull(lineItem1);
        assertEquals(2, lineItem1.getId());
        assertEquals(2, lineItem1.getGoods().getId());
        assertEquals("400", lineItem1.getOrders().getId());
        assertEquals(30, lineItem1.getQuantity());
        assertEquals(6000, lineItem1.getSubTotal());
    }

    @Test
    void modify() {
        OrderLineItem lineItem = new OrderLineItem();
        lineItem.setId(2);
        lineItem.setQuantity(300);
        lineItem.setSubTotal(60000);

        Goods goods = new Goods();
        goods.setId(2);
        lineItem.setGoods(goods);

        Orders orders = new Orders();
        orders.setId("400");
        lineItem.setOrders(orders);

        lineItemDao.modify(lineItem);

        OrderLineItem lineItem1 = lineItemDao.findById(2);
        assertNotNull(lineItem1);
        assertEquals(2, lineItem1.getId());
        assertEquals(2, lineItem1.getGoods().getId());
        assertEquals("400", lineItem1.getOrders().getId());
        assertEquals(300, lineItem1.getQuantity());
        assertEquals(60000, lineItem1.getSubTotal());
    }

    @Test
    void delete() {

        lineItemDao.delete(2);

        OrderLineItem lineItem = lineItemDao.findById(2);
        assertNull(lineItem);
    }
}