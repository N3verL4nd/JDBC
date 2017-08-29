package com.xiya.entity;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by N3verL4nd on 2017/4/17.
 */
public class Person {
    private int id;
    private String name;
    private int age;
    private Date birth;
    private String email;

    public void setSex(String dsd) {
    }

    public Person() {
    }

    public Person(int id, String name, int age, Date birth, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.email = email;
    }

    public Person(String name, int age, Date birth, String email) {
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birth=" + /*(birth == null ? null : birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())*/ birth +
                ", email='" + email + '\'' +
                '}';
    }
}
