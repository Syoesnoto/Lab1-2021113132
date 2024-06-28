package com.hit_se.se_project.user;

public class Student extends User {
    public Student(String name, String password, boolean permission) {
        super(name, password, permission);
        type = "student";
    }
}
