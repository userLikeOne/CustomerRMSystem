package com.dxf.springboot.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxf.springboot.common.R;
import com.dxf.springboot.entity.Customer;
import com.dxf.springboot.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        // 1.创建page对象，分页构造器
        Page<Customer> pageInfo = new Page<>(page, pageSize);
        // 2.条件构造器：对姓名模糊查询
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Customer::getName,name);
        // 3.执行分页查询
        customerService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PostMapping
    public R<String> save(@RequestBody Customer customer){
        log.info("添加客户");
        customerService.save(customer);
        return R.success("客户添加成功");
    }

    @DeleteMapping
    public R<String> remove(long ids[]){
        Long[] longObjects = ArrayUtils.toObject(ids);
        List<Long> longList = java.util.Arrays.asList(longObjects);
        boolean ret = customerService.removeByIds(longList);
        if (ret){
            return R.success("客户删除成功!");
        } else {
            return R.success("客户删除失败!");
        }
    }


    @PutMapping
    public R<String> edit(@RequestBody Customer customer){
        log.info(customer.toString());
        customerService.updateById(customer);
        return R.success("客户修改成功");
    }

    @GetMapping("/{id}")
    public R<Customer> get(@PathVariable long id){
        Customer customer = customerService.getById(id);
        if (customer!=null){
            return R.success(customer);
        }
        return R.error("没有查到对应员工信息");
    }


    @PostMapping("/output")
    public R<String> ceshi(){
        List<Customer> all = customerService.getAll();
        String fileName = "E:\\" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, Customer.class).sheet("模板").doWrite(all);


        return R.success("文件导出成功！");
    }

}


