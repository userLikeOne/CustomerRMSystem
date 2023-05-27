package com.dxf.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxf.springboot.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
