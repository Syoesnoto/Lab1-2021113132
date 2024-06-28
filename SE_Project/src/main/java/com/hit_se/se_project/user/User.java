package com.hit_se.se_project.user;

public abstract class User {
    private String name;  //用户名
    protected String type;  //用户类型
    private String password;  //用户密码
    private boolean permission;

    public User(String name, String password, boolean permission) {
        this.name = name;
        this.password = password;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}