package com.sam.store.service.impl;

import com.sam.store.dao.CustomerDao;
import com.sam.store.dao.impl.CustomerDaoImplJdbc;
import com.sam.store.pojo.Customer;
import com.sam.store.service.CustomerService;
import com.sam.store.service.ServiceException;

public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao = new CustomerDaoImplJdbc();

    @Override
    public boolean login(Customer customer) {

        Customer dbCustomer = customerDao.findById(customer.getId());

        if(dbCustomer != null && dbCustomer.getPassword().equals(customer.getPassword())){

            //登入成功
            customer.setPhone(dbCustomer.getPhone());
            customer.setAddress(dbCustomer.getAddress());
            customer.setName(dbCustomer.getName());
            customer.setBirthday(dbCustomer.getBirthday());


            return  true;
        }

        return false;
    }

    @Override
    public void register(Customer customer) throws ServiceException {

        Customer dbCustomer = customerDao.findById(customer.getId());

        if(dbCustomer != null){
            throw new ServiceException("客戶id" + customer.getId() + "已經存在！！");
        }

        customerDao.create(customer);
    }
}
