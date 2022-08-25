package com.sam.store.service.impl;

import com.sam.store.dao.GoodsDao;
import com.sam.store.dao.OrderDao;
import com.sam.store.dao.OrderLineItemDao;
import com.sam.store.dao.impl.GoodsDaoImplJdbc;
import com.sam.store.dao.impl.OrderDaoImpJdbc;
import com.sam.store.dao.impl.OrderLineItemDaoImplJdbc;
import com.sam.store.pojo.Goods;
import com.sam.store.pojo.OrderLineItem;
import com.sam.store.pojo.Orders;
import com.sam.store.service.OrdersService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrdersServiceImpl implements OrdersService {

    OrderDao orderDao = new OrderDaoImpJdbc();
    GoodsDao goodsDao = new GoodsDaoImplJdbc();
    OrderLineItemDao orderLineItemDao = new OrderLineItemDaoImplJdbc();



    @Override
    public String submitOrders(List<Map<String, Object>> cart) {

        Orders orders = new Orders();
        Date date = new Date();//當前日期

        //訂單id：當前時間加上亂數
        String ordersid = String.valueOf(date.getTime()) + String.valueOf((int)(Math.random() * 100));

        //設置訂單資訊
        orders.setId(ordersid);
        orders.setOrderDate(date);
        orders.setTotal(0.0);
        orders.setStatus(1);//1為未付款

        //訂單訊息插入到資料庫
        orderDao.create(orders);

        double total = 0.0;

        for(Map item : cart){
            //item結構[商品id, 數量]
            Long goodsid = (Long) item.get("goodsid");
            Integer quantity = (Integer) item.get("quantity");
            Goods goods = goodsDao.findById(goodsid);
            //總金額(數量*商品價格)
            double subTotal = quantity * goods.getPrice();
            total += subTotal;

            OrderLineItem lineItem = new OrderLineItem();
//            lineItem.setId();  ==> AI
            lineItem.setQuantity(quantity);
            lineItem.setGoods(goods);
            lineItem.setOrders(orders);
            lineItem.setSubTotal(subTotal);

            //將訂單詳細訊息插入到資料庫
            orderLineItemDao.create(lineItem);
        }

        //金額更新
        orders.setTotal(total);
        //更新資料庫(金額修改)
        orderDao.modify(orders);

        return ordersid;//回傳商品id
    }
}
