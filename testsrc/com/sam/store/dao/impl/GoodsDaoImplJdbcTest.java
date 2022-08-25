package com.sam.store.dao.impl;

import com.sam.store.dao.GoodsDao;
import com.sam.store.pojo.Goods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoodsDaoImplJdbcTest {

    GoodsDao goodsDao;

    @BeforeEach
    void setUp() {
        goodsDao = new GoodsDaoImplJdbc();
    }

    @AfterEach
    void tearDown() {
        goodsDao = null;
    }

    @Test
    void findById() {
        Goods goods = goodsDao.findById(1);
        assertNotNull(goods);
        assertEquals(1, goods.getId());
        assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());
        assertEquals(3399, goods.getPrice());
        assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英寸 ", goods.getDescription());
        assertEquals("", goods.getBrand());
        assertEquals("", goods.getCpuBrand());
        assertEquals("", goods.getCpuType());
        assertEquals("", goods.getMemoryCapacity());
        assertEquals("", goods.getHdCapacity());
        assertEquals("", goods.getCardModel());
        assertEquals("", goods.getDisplaysize());
        assertEquals("5ae00211N25afad2c.jpg", goods.getImage());
    }

    @Test
    void findAll() {
        List<Goods> list = goodsDao.findAll();
        assertEquals(34, list.size());

        Goods goods = list.get(0);
        assertNotNull(goods);
        assertEquals(1, goods.getId());
        assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());
        assertEquals(3399, goods.getPrice());
        assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英寸 ", goods.getDescription());
        assertEquals("", goods.getBrand());
        assertEquals("", goods.getCpuBrand());
        assertEquals("", goods.getCpuType());
        assertEquals("", goods.getMemoryCapacity());
        assertEquals("", goods.getHdCapacity());
        assertEquals("", goods.getCardModel());
        assertEquals("", goods.getDisplaysize());
        assertEquals("5ae00211N25afad2c.jpg", goods.getImage());
    }

    @Test
    void findStartEnd() {
        List<Goods> list = goodsDao.findStartEnd(0, 10);
        assertEquals(10, list.size());

        Goods goods = list.get(0);
        assertNotNull(goods);
        assertEquals(1, goods.getId());
        assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());
        assertEquals(3399, goods.getPrice());
        assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英寸 ", goods.getDescription());
        assertEquals("", goods.getBrand());
        assertEquals("", goods.getCpuBrand());
        assertEquals("", goods.getCpuType());
        assertEquals("", goods.getMemoryCapacity());
        assertEquals("", goods.getHdCapacity());
        assertEquals("", goods.getCardModel());
        assertEquals("", goods.getDisplaysize());
        assertEquals("5ae00211N25afad2c.jpg", goods.getImage());
    }

    @Test
    void create() {
        Goods goods = new Goods();
        goods.setId(35);
        goods.setName("iphone 10");
        goods.setPrice(30000);
        goods.setDescription("蘋果iphone10");
        goods.setBrand("蘋果");
        goods.setCpuBrand("Intel");
        goods.setCpuType("i5");
        goods.setMemoryCapacity("8G");
        goods.setHdCapacity("500G");
        goods.setCardModel("GTX");
        goods.setDisplaysize("無");
        goods.setImage("apple.jpg");

        goodsDao.create(goods);

        Goods goods1 = goodsDao.findById(35);
        assertNotNull(goods1);
        assertEquals(35L, goods1.getId());
        assertEquals("iphone 10", goods1.getName());
        assertEquals(30000.00, goods1.getPrice());
        assertEquals("蘋果iphone10", goods1.getDescription());
        assertEquals("蘋果", goods1.getBrand());
        assertEquals("Intel",goods1.getCpuBrand());
        assertEquals("i5",goods1.getCpuType());
        assertEquals("8G",goods1.getMemoryCapacity());
        assertEquals("500G",goods1.getHdCapacity());
        assertEquals("GTX",goods1.getCardModel());
        assertEquals("無",goods1.getDisplaysize());
        assertEquals("apple.jpg", goods1.getImage());
    }

    @Test
    void modify() {///要照順序修改，不能中間有空然後修改後面的
        Goods goods = new Goods();
        goods.setId(35);
        goods.setName("iphone 10000");
        goods.setPrice(30000.00);
        goods.setDescription("蘋果iphone10");
        goods.setBrand("蘋果");
        goods.setCpuBrand("Intel");
        goods.setCpuType("i5");
        goods.setMemoryCapacity("8G");
        goods.setHdCapacity("500G");
        goods.setCardModel("GTX");
        goods.setDisplaysize("無");
        goods.setImage("apple.jpg");

        goodsDao.modify(goods);

        Goods goods1 = goodsDao.findById(35);
        assertNotNull(goods1);
        assertEquals(35, goods1.getId());
        assertEquals("iphone 10000", goods1.getName());
        assertEquals(30000, goods1.getPrice());
        assertEquals("蘋果iphone10", goods1.getDescription());
        assertEquals("蘋果", goods1.getBrand());
        assertEquals("Intel",goods1.getCpuBrand());
        assertEquals("i5",goods1.getCpuType());
        assertEquals("8G",goods1.getMemoryCapacity());
        assertEquals("500G",goods1.getHdCapacity());
        assertEquals("GTX",goods1.getCardModel());
        assertEquals("無",goods1.getDisplaysize());
        assertEquals("apple.jpg", goods1.getImage());
    }

    @Test
    void delete() {
        goodsDao.delete(35);

        Goods goods = goodsDao.findById(35);
        assertNull(goods);
    }
}