package com.hit_se.se_project;
import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;
import com.hit_se.se_project.question.exam.ExamEntry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
class main {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(main.class, args);
    }
}