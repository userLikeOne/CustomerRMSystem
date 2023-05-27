package com.dxf.springboot.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("contact")
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelProperty("客户id")
    private Long id;
    @ExcelProperty("客户姓名")
    private String name;
    @ExcelProperty("性别")
    private Integer gender;
    @ExcelProperty("手机号")
    private String phone;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("地址")
    private String address;
    @ExcelProperty("联系人id")
    @TableField("customerId")
    private Long customerId;
}
