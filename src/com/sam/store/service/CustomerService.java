package com.sam.store.service;

import com.sam.store.pojo.Customer;

public interface CustomerService {

    boolean login(Customer customer);

    void register(Customer customer) throws ServiceException;
}
