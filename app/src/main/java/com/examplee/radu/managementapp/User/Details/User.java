package com.examplee.radu.managementapp.User.Details;

import java.util.Random;

public class User {
    String name;
    int age;
    String title;
    int salary;
    String project;

    public User(String name, int age) {
        this.title = title;
        this.name = name;
        this.age = age;
        this.salary = 0;
        this.project = "Default";
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getTitle() {
        return title;
    }

    public double getSalary() {
        return salary;
    }

    public String getProject() {
        return project;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
