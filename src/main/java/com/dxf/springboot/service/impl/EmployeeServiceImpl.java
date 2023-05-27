package com.dxf.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxf.springboot.entity.Employee;
import com.dxf.springboot.mapper.EmployeeMapper;
import com.dxf.springboot.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
