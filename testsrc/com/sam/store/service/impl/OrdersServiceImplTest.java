package com.sam.store.service.impl;

import com.sam.store.dao.OrderDao;
import com.sam.store.dao.OrderLineItemDao;
import com.sam.store.dao.impl.OrderDaoImpJdbc;
import com.sam.store.dao.impl.OrderLineItemDaoImplJdbc;
import com.sam.store.pojo.OrderLineItem;
import com.sam.store.pojo.Orders;
import com.sam.store.service.OrdersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrdersServiceImplTest {

    OrdersService ordersService;

    OrderDao orderDao = new OrderDaoImpJdbc();
    OrderLineItemDao orderLineItemDao = new OrderLineItemDaoImplJdbc();

    @BeforeEach
    void setUp() {
        ordersService = new OrdersServiceImpl();
    }

    @AfterEach
    void tearDown() {
        ordersService = null;
    }

    @Test
    void submitOrders() {

        List<Map<String, Object>> cart = new ArrayList<Map<String, Object>>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("goodsid", 3);
        item1.put("quantity", 2);
        cart.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("goodsid", 8);
        item2.put("quantity", 3);
        cart.add(item2);

        String ordersid = ordersService.submitOrders(cart);

        assertNotNull(ordersid);//訂單id為隨機數，因此我們無法用準確的數斷言

        Orders orders = orderDao.findById(ordersid);//去查訂單表有沒有這個數據
        assertNotNull(orders);
        assertEquals(1, orders.getStatus());
        //日期無法斷言
        double total = (3099 * 2) + (1888 * 3);
        assertEquals(total, orders.getTotal());

        List<OrderLineItem> list = orderLineItemDao.findAll();
        List<OrderLineItem> orderLineItemList = new ArrayList<>();
        for(OrderLineItem lineItem : list) {
            if (lineItem.getOrders().getId().equals(ordersid)) {//如果商品詳細訊息的商品id一樣的話
                orderLineItemList.add(lineItem);//則加到list中

                if (lineItem.getGoods().getId() == 3) {
                    assertEquals(2, lineItem.getQuantity());//數量
                    assertEquals(3099 * 2, lineItem.getSubTotal());//總金額
                }

                if (lineItem.getGoods().getId() == 8) {
                    assertEquals(3, lineItem.getQuantity());
                    assertEquals(1888 * 3, lineItem.getSubTotal());
                }
            }
        }
        assertEquals(2, orderLineItemList.size());
    }


}