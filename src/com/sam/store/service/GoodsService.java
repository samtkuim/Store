package com.sam.store.service;

import com.sam.store.pojo.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> queryAll();

    List<Goods> queryByStartEnd(int start, int end);

    Goods queryDetail(long goodsid);
}
