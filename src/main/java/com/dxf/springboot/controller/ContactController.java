package com.dxf.springboot.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxf.springboot.common.R;
import com.dxf.springboot.entity.Contact;
import com.dxf.springboot.entity.ContactDto;
import com.dxf.springboot.entity.Customer;
import com.dxf.springboot.service.ContactService;
import com.dxf.springboot.service.CustomerService;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactService contactService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        // 1.创建page对象，分页构造器
        Page<Contact> pageInfo = new Page<>(page, pageSize);
        // 2.条件构造器：对姓名模糊查询
        LambdaQueryWrapper<Contact> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Contact::getName,name);
        // 3.执行分页查询
        contactService.page(pageInfo,queryWrapper);
        Page<ContactDto> resPage = new Page(page, pageSize);
        BeanUtils.copyProperties(pageInfo,resPage,"records");
        resPage.setRecords(pageInfo.getRecords().stream().map(item -> {
            ContactDto contactDto = new ContactDto();
            BeanUtils.copyProperties(item,contactDto);
            Customer customer=customerService.getById(item.getCustomerId());
            if (customer!=null){
                contactDto.setCustomerName(customer.getName());
            }
            return contactDto;
        }).collect(Collectors.toList()));

        return R.success(resPage);
    }

    @PostMapping
    public R<String> save(@RequestBody Contact contact){
        log.info("添加联系人");
        contactService.save(contact);
        return R.success("联系人添加成功");
    }

    @DeleteMapping
    public R<String> remove(long ids[]){
        Long[] longObjects = ArrayUtils.toObject(ids);
        List<Long> longList = java.util.Arrays.asList(longObjects);
        boolean ret = contactService.removeByIds(longList);
        if (ret){
            return R.success("联系人删除成功!");
        } else {
            return R.success("联系人删除失败!");
        }
    }


    @PutMapping
    public R<String> edit(@RequestBody Contact contact){
        log.info(contact.toString());
        contactService.updateById(contact);
        return R.success("联系人修改成功");
    }

    @GetMapping("/{id}")
    public R<Contact> get(@PathVariable long id){
        Contact contact = contactService.getById(id);
        if (contact!=null){
            return R.success(contact);
        }
        return R.error("没有查到对应联系人信息");
    }


}


