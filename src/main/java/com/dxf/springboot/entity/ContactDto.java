package com.dxf.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactDto extends Contact implements Serializable {
    private String customerName;
}
