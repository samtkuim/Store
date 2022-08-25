package com.sam.store.service.impl;

import com.sam.store.dao.GoodsDao;
import com.sam.store.dao.impl.GoodsDaoImplJdbc;
import com.sam.store.pojo.Goods;
import com.sam.store.service.GoodsService;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {

    GoodsDao goodsDao = new GoodsDaoImplJdbc();

    @Override
    public List<Goods> queryAll() {
        return goodsDao.findAll();
    }

    @Override
    public List<Goods> queryByStartEnd(int start, int end) {
        return goodsDao.findStartEnd(start, end);
    }

    @Override
    public Goods queryDetail(long goodsid) {
        return goodsDao.findById(goodsid);
    }
}
