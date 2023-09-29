package com.duan.demo01.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Warehouse {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String address;

}
