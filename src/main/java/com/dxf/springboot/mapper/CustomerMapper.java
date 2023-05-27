package com.dxf.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxf.springboot.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    @Select("select * from customer")
    public List<Customer> findAll();


}
