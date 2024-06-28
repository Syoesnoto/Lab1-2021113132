package com.hit_se.se_project.controller;

import com.alibaba.fastjson.JSONObject;
import com.hit_se.se_project.question.exam.ExamEntry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/response")
public class ResponseController extends ExamController {

    @RequestMapping("/create")
    public void create(@RequestParam("name")String name, @RequestParam("username")String username) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + username + "_" + name + " SELECT * FROM " + name;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    @RequestMapping("/save")
    public void response(@RequestParam("name")String name, @RequestParam("data")String data, @RequestParam("answer")String answer) throws SQLException {
        JSONObject json = JSONObject.parseObject(data);
        ExamEntry examEntry = new ExamEntry(json);
        examEntry.response(answer);
        modify(name, examEntry);
    }
}
