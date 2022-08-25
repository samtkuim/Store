package com.sam.store.service.impl;

import com.sam.store.pojo.Goods;
import com.sam.store.service.GoodsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoodsServiceImplTest {

    GoodsService goodsService;

    @BeforeEach
    void setUp() {
        goodsService = new GoodsServiceImpl();
    }

    @AfterEach
    void tearDown() {
        goodsService = null;
    }

    @Test
    void queryAll() {
        List<Goods> list = goodsService.queryAll();
        assertEquals(34, list.size());

        Goods goods = list.get(2);
        assertNotNull(goods);
        assertEquals(3, goods.getId());
        assertEquals("聯想天逸510S", goods.getName());
        assertEquals(3099, goods.getPrice());
        assertEquals("聯想（Lenovo）天逸510S商用台式辦公電腦整機（i3-7100 4G 1T 集顯 WiFi 藍牙 三年上門 win10）19.5英寸", goods.getDescription());
        assertEquals("聯想（Lenovo）", goods.getBrand());
        assertEquals("Intel ", goods.getCpuBrand());
        assertEquals("Intel i3", goods.getCpuType());
        assertEquals("4G", goods.getMemoryCapacity());
        assertEquals("1T", goods.getHdCapacity());
        assertEquals("集成顯卡", goods.getCardModel());
        assertEquals("", goods.getDisplaysize());
        assertEquals("5a6e946eNd622e938.jpg", goods.getImage());
    }

    @Test
    void queryByStartEnd() {
        List<Goods> list = goodsService.queryByStartEnd(0, 10);
        assertEquals(10, list.size());

        Goods goods = list.get(2);
        assertNotNull(goods);
        assertEquals(3, goods.getId());
        assertEquals("聯想天逸510S", goods.getName());
        assertEquals(3099, goods.getPrice());
        assertEquals("聯想（Lenovo）天逸510S商用台式辦公電腦整機（i3-7100 4G 1T 集顯 WiFi 藍牙 三年上門 win10）19.5英寸", goods.getDescription());
        assertEquals("聯想（Lenovo）", goods.getBrand());
        assertEquals("Intel ", goods.getCpuBrand());
        assertEquals("Intel i3", goods.getCpuType());
        assertEquals("4G", goods.getMemoryCapacity());
        assertEquals("1T", goods.getHdCapacity());
        assertEquals("集成顯卡", goods.getCardModel());
        assertEquals("", goods.getDisplaysize());
        assertEquals("5a6e946eNd622e938.jpg", goods.getImage());
    }

    @Test
    void queryDetail() {
        Goods goods = goodsService.queryDetail(3);
        assertNotNull(goods);
        assertEquals(3, goods.getId());
        assertEquals("聯想天逸510S", goods.getName());
        assertEquals(3099, goods.getPrice());
        assertEquals("聯想（Lenovo）天逸510S商用台式辦公電腦整機（i3-7100 4G 1T 集顯 WiFi 藍牙 三年上門 win10）19.5英寸", goods.getDescription());
        assertEquals("聯想（Lenovo）", goods.getBrand());
        assertEquals("Intel ", goods.getCpuBrand());
        assertEquals("Intel i3", goods.getCpuType());
        assertEquals("4G", goods.getMemoryCapacity());
        assertEquals("1T", goods.getHdCapacity());
        assertEquals("集成顯卡", goods.getCardModel());
        assertEquals("", goods.getDisplaysize());
        assertEquals("5a6e946eNd622e938.jpg", goods.getImage());
    }
}