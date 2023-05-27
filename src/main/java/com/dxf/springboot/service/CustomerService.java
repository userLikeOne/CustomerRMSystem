package com.dxf.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dxf.springboot.entity.Customer;

import java.util.List;

public interface CustomerService extends IService<Customer> {
    List<Customer> getAll();
}
