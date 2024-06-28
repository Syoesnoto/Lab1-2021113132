package com.hit_se.se_project.user;

import com.hit_se.se_project.controller.UserController;

import java.sql.SQLException;

public class Admin extends User {
    public Admin(String name, String password, boolean permission) {
        super(name, password, permission);
        type = "admin";
    }

    public void setPermission(User user, boolean permission) throws SQLException {
        user.setPermission(permission);
        UserController userController = new UserController();
        userController.modify(user);
    }
}
