package com.sam.store.dao.impl;

import com.sam.store.dao.CustomerDao;
import com.sam.store.pojo.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoImplJdbcTest {

    CustomerDao customerDao;

    @BeforeEach//被註解的方法將在當前類中的每個 @Test 方法前執行
    void setUp() {
        customerDao = new CustomerDaoImplJdbc();
    }

    @AfterEach//被註解的方法將在當前類中的每個 @Test 方法後執行。
    void tearDown() {
        customerDao = null;
    }

    @Test
    void findById() {
        Customer customer = customerDao.findById("sam");
        assertNotNull(customer);
        assertEquals("sam", customer.getId());
        assertEquals("黃奕翔", customer.getName());
        assertEquals("123", customer.getPassword());
        assertEquals("新莊區", customer.getAddress());
        assertEquals("0917", customer.getPhone());
        assertEquals(1026, customer.getBirthday().getTime());
    }

    @Test
    void findAll() {
        List<Customer> list = customerDao.findAll();
        assertEquals(list.size(), 1);//看數據庫有幾筆資料
    }

    @Test
    void create() {
        Customer customer = new Customer();
        customer.setId("tom");
        customer.setName("湯姆");
        customer.setPassword("123");
        customer.setAddress("新莊區");
        customer.setPhone("0926");
        customer.setBirthday(new Date(1103));

        customerDao.create(customer);

        Customer customer1 = customerDao.findById("tom");
        assertNotNull(customer1);
        assertEquals("tom", customer1.getId());
        assertEquals("湯姆", customer1.getName());
        assertEquals("123", customer1.getPassword());
        assertEquals("新莊區", customer1.getAddress());
        assertEquals("0926", customer1.getPhone());
        assertEquals(1103, customer1.getBirthday().getTime());
    }

    @Test
    void modify() {
        Customer customer = new Customer();
        customer.setId("tom");
        customer.setName("湯姆2");
        customer.setPassword("222");
        customer.setAddress("板橋區");
        customer.setPhone("0955");
        customer.setBirthday(new Date(1225));

        customerDao.modify(customer);

        Customer customer1 = customerDao.findById("tom");
        assertNotNull(customer1);
        assertEquals("tom", customer1.getId());
        assertEquals("湯姆2", customer1.getName());
        assertEquals("222", customer1.getPassword());
        assertEquals("板橋區", customer1.getAddress());
        assertEquals("0955", customer1.getPhone());
        assertEquals(1225, customer1.getBirthday().getTime());


    }

    @Test
    void delete() {
        customerDao.delete("tom");

        Customer customer = customerDao.findById("tom");
        assertNull(customer);
    }
}