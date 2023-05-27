package com.dxf.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private Integer role;

    private String phone;

    private String sex;

    private String name;
}
