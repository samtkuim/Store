package com.sam.store.service.impl;

import com.sam.store.pojo.Customer;
import com.sam.store.service.CustomerService;
import com.sam.store.service.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl();
    }

    @AfterEach
    void tearDown() {
        customerService = null;
    }

    @Test
    @DisplayName("登入成功")
    void login1() {

        Customer customer = new Customer();
        customer.setId("sam");
        customer.setPassword("123");

        assertTrue(customerService.login(customer));

        assertNotNull(customer);
        assertEquals("sam", customer.getId());
        assertEquals("黃奕翔", customer.getName());
        assertEquals("123", customer.getPassword());
        assertEquals("新莊區", customer.getAddress());
        assertEquals("0917", customer.getPhone());
        assertEquals(1026, customer.getBirthday().getTime());
    }

    @Test
    @DisplayName("登入失敗")
    void login2() {

        Customer customer = new Customer();
        customer.setId("sam");
        customer.setPassword("123444");

        assertFalse(customerService.login(customer));
    }

    @Test
    @DisplayName("註冊成功")
    void register1() throws ServiceException {

        Customer customer = new Customer();
        customer.setId("ena");
        customer.setName("蘇");
        customer.setPassword("11");
        customer.setAddress("板橋");
        customer.setPhone("0911");
        customer.setBirthday(new Date(0715));

        customerService.register(customer);

        //利用登入來判斷是否註冊成功
        Customer customer1 = new Customer();
        customer1.setId("ena");
        customer1.setPassword("11");

        assertTrue(customerService.login(customer1));
        assertNotNull(customer1);
        assertEquals("ena", customer1.getId());
        assertEquals("蘇", customer1.getName());
        assertEquals("11", customer1.getPassword());
        assertEquals("板橋", customer1.getAddress());
        assertEquals("0911", customer1.getPhone());
        assertEquals(0715, customer1.getBirthday().getTime());

    }

    @Test
    @DisplayName("註冊失敗(id重複)")
    void register2() {

        Customer customer = new Customer();
        customer.setId("ena");
        customer.setPassword("11");


        assertThrows(ServiceException.class, ()-> {
            customerService.register(customer);
        });
    }
}