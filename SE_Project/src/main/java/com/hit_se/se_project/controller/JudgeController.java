package com.hit_se.se_project.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hit_se.se_project.question.exam.Exam;
import com.hit_se.se_project.question.exam.ExamEntry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/judge")
public class JudgeController extends ExamController {
    @RequestMapping("/personal")
    public void judge(@RequestParam("name")String name, @RequestParam("data")String data, @RequestParam("pointID")String pointID) throws SQLException {
        JSONObject json = JSONObject.parseObject(data);
        ExamEntry examEntry = new ExamEntry(json);
        int point = Integer.parseInt(pointID);
        examEntry.judge(point);
        modify(name, examEntry);
    }

    @RequestMapping("/auto")
    public void autoJudge(@RequestParam("data")String data) throws SQLException {
        JSONObject json = JSONObject.parseObject(data);
        Exam exam = new Exam(json);
        String name = exam.getName();
        for (ExamEntry examEntry : exam.getChoiceEntries()) {
            examEntry.autoJudge();
            modify(name, examEntry);
        }
        for (ExamEntry examEntry : exam.getTrueFalseEntries()) {
            examEntry.autoJudge();
            modify(name, examEntry);
        }
    }
}
