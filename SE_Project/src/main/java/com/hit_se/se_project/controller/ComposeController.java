package com.hit_se.se_project.controller;

import com.hit_se.se_project.question.Question;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/compose")
public class ComposeController extends ExamController{
    @RequestMapping("/create")
    public void create(@RequestParam("name")String name) throws SQLException {
        String sql = "CREATE TABLE " + name + " (" +
                    "question INT PRIMARY KEY," +
                    "type INT NOT NULL," +
                    "answer VARCHAR(2000)," +
                    "done BOOLEAN DEFAULT FALSE," +
                    "`right` BOOLEAN DEFAULT FALSE," +
                    "score INT," +
                    "point INT DEFAULT 0);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    @RequestMapping("/add")
    public void compose(@RequestParam("name")String name, @RequestParam("question")String questionDetail, @RequestParam("score")String score) throws SQLException {
        int num = questionDetail.hashCode();
        ResultSet rs = findQuestion(num);
        rs.next();
        Question question = questionController.create(rs);
        add(name, question, score);
    }
}
