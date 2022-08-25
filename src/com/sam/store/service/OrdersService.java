package com.sam.store.service;

import java.util.List;
import java.util.Map;

public interface OrdersService {

    //提交訂單(商品id)
    String submitOrders(List<Map<String, Object>> cart);
}
