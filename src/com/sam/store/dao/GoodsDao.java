package com.sam.store.dao;

import com.sam.store.pojo.Goods;

import java.util.List;

public interface GoodsDao {

    Goods findById(long id);

    List<Goods> findAll();

    /**
     * 提供分頁查詢
     * @param start 開始索引 索引從0開始
     * @param end   結束索引 索引從0開始
     * @return 返回商品列表
     */
    List<Goods> findStartEnd(int start, int end);

    void create(Goods goods);

    void modify(Goods goods);

    void delete(long id);
}
