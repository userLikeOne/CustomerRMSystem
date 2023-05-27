package com.dxf.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxf.springboot.entity.Customer;
import com.dxf.springboot.mapper.CustomerMapper;
import com.dxf.springboot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    CustomerMapper customerMapper;

    @Override
    public List<Customer> getAll(){
        return customerMapper.findAll();
    }
}
