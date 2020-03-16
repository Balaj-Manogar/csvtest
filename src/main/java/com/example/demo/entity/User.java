package com.example.demo.entity;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @CsvBindByName(column = "Id")
    private Integer id;

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Full name")
    @Column(name = "new_name")
    private String newName;

    public User(){}

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
