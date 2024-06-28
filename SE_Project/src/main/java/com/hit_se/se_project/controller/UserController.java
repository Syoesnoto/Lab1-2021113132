package com.hit_se.se_project.controller;

import com.hit_se.se_project.question.BlankFillingQuestion;
import com.hit_se.se_project.question.ChoiceQuestion;
import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.TrueFalseQuestion;
import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;
import com.hit_se.se_project.user.Admin;
import com.hit_se.se_project.user.Student;
import com.hit_se.se_project.user.Teacher;
import com.hit_se.se_project.user.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RequestMapping("/user")
@RestController
public class UserController extends Connector {
    public ArrayList<User> search() throws SQLException {
        ResultSet rs = findUsers();
        ArrayList<User> ls = new ArrayList<>();
        while (rs.next()) {
            ls.add(create(rs));
        }
        return ls;
    }

    public User create(ResultSet rs) throws SQLException {
        User u = null;
        String name = rs.getString("name");
        String type = rs.getString("type");
        String password = rs.getString("password");
        boolean permission = rs.getBoolean("permission");

        switch (type) {
            case "student":
                u = new Student(name, password, permission);
                break;
            case "teacher":
                u = new Teacher(name, password, permission);
                break;
            case "admin":
                u = new Admin(name, password, permission);
                break;
        }

        return u;
    }

    public void add(User user) throws SQLException {
        String sql = "INSERT INTO user (name, type, password, permission) VALUES (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getType());
        pstmt.setString(3, user.getPassword());
        pstmt.setBoolean(4, user.isPermission());
        pstmt.executeUpdate();
    }

    public void delete(String name) throws SQLException {
        String sql = "DELETE FROM user WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.executeUpdate();
    }

    public void modify(User user) throws SQLException {
        delete(user.getName());
        add(user);
    }

    @RequestMapping("/login")
    public void login(HttpServletResponse resp, @RequestParam("username") String name, @RequestParam("password") String password) throws SQLException, IOException, IOException {
        rs = findUser(name);
        PrintWriter respWriter = resp.getWriter();
        if (!rs.next()) {
            respWriter.append("false");
        } else if (password.equals(rs.getString("password"))) {
            User user = create(rs);
            JSONObject data = JSONObject.fromObject(user);
            resp.setCharacterEncoding("utf-8");
            /*得到输出流*/
            respWriter.append(data.toString());
        } else{
            respWriter.append("false");
        }
    }

    @RequestMapping("/register")
    public int register(@RequestParam("username") String name, @RequestParam("usertype") String type, @RequestParam("password") String password, @RequestParam("confirm-password") String conpwd) throws SQLException {
        if(!password.equals(conpwd)) {
            return 1;
        }
        rs = findUser(name);
        if (rs.next()) {
            return 2;
        } else {
            String sql = "INSERT INTO user (name,type,password, permission) VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setString(3, password);
            pstmt.setBoolean(4, true);
            pstmt.executeUpdate();
            return 3;
        }
    }
}
