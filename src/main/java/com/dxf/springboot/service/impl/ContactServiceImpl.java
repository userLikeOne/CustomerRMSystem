package com.dxf.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxf.springboot.entity.Contact;
import com.dxf.springboot.entity.Customer;
import com.dxf.springboot.mapper.ContactMapper;
import com.dxf.springboot.mapper.CustomerMapper;
import com.dxf.springboot.service.ContactService;
import com.dxf.springboot.service.CustomerService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {
}
