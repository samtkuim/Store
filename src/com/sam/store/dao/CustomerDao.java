package com.sam.store.dao;

import com.sam.store.pojo.Customer;

import java.util.List;

public interface CustomerDao {

    //透過id查詢
    Customer findById(String id);

    //查詢全部
    List<Customer> findAll();

    //建造
    void create(Customer customer);

    //修改
    void modify(Customer customer);

    //刪除
    void delete(String id);
}
