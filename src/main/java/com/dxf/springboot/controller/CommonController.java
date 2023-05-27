package com.dxf.springboot.controller;

import com.alibaba.excel.EasyExcel;
import com.dxf.springboot.common.R;
import com.dxf.springboot.entity.Customer;
import com.dxf.springboot.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    HttpServletRequest request;
    String basePath = "D:\\upload\\";

    @Autowired
    CustomerService customerService;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.toString());

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring((originalFilename.lastIndexOf(".")));

        String fileName = UUID.randomUUID().toString()+suffix;

        File dir = new File(basePath);

        if (!dir.exists()){
            dir.mkdirs();
        }
        try{
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e){
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    @PostMapping("/download")
    public void downloadd(HttpServletResponse response) throws IOException{
        download(response);
    }

    public void download(HttpServletResponse response) throws IOException {
        List<Customer> data = customerService.getAll();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("ExcelName", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        response.setHeader("fileName",  fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Customer.class).sheet("sheetName").doWrite(data);
    }
}
