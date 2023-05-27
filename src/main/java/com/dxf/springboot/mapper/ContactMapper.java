package com.dxf.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxf.springboot.entity.Contact;
import com.dxf.springboot.entity.Customer;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContactMapper extends BaseMapper<Contact> {
}
