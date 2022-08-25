package com.sam.store.dao;

import com.sam.store.pojo.OrderLineItem;

import java.util.List;

public interface OrderLineItemDao {

    OrderLineItem findById(long id);

    List<OrderLineItem> findAll();

    void create(OrderLineItem lineItem);

    void modify(OrderLineItem lineItem);

    void delete(long id);
}
