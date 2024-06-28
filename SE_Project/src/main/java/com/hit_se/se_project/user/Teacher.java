package com.hit_se.se_project.user;

public class Teacher extends User {
    public Teacher(String name, String password, boolean permission) {
        super(name, password, permission);
        type = "teacher";
    }
}