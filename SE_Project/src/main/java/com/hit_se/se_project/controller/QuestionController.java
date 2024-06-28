package com.hit_se.se_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit_se.se_project.question.BlankFillingQuestion;
import com.hit_se.se_project.question.ChoiceQuestion;
import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.TrueFalseQuestion;
import com.hit_se.se_project.question.assistant.Choice;
import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;
import com.hit_se.se_project.question.exam.ExamEntry;
import net.sf.json.JSONArray;
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

@RestController
@RequestMapping("/questions")
public class QuestionController extends Connector {
    @RequestMapping("/search")
    public void search(HttpServletResponse resp) throws SQLException, IOException {
        ResultSet rs = findQuestions();
        ArrayList<Question> ls = new ArrayList<>();
        while (rs.next()) {
            Question question = create(rs);
            ls.add(question);
        }
        /*将list集合装换成json对象*/
        JSONArray data = JSONArray.fromObject(ls);
        //接下来发送数据
        //  /*设置编码，防止出现乱码问题*/
        resp.setCharacterEncoding("utf-8");
        /*得到输出流*/
        PrintWriter respWriter = resp.getWriter();
        /*将JSON格式的对象toString()后发送*/
        respWriter.append(data.toString());
    }

    public Question create(ResultSet rs) throws SQLException {
        Question q = null;
        int num = rs.getInt("num");
        String question = rs.getString("question");
        String answer = rs.getString("answer");
        Type type = Type.convert(rs.getInt("type"));
        Difficulty difficulty = Difficulty.convert(rs.getInt("difficulty"));
        String knowledge = rs.getString("knowledge");

        switch (type) {
            case choice:
                q = new ChoiceQuestion(num, question, answer, type, difficulty, knowledge);
                break;
            case true_false:
                q = new TrueFalseQuestion(num, question, answer, type, difficulty, knowledge);
                break;
            case fill_in_blank:
                q = new BlankFillingQuestion(num, question, answer, type, difficulty, knowledge);
                break;
        }

        return q;
    }

    @RequestMapping("/add")
    public int add(@RequestParam("updatedQuestion")String question, @RequestParam("updatedAnswer")String answer,
                   @RequestParam("updatedType")Type type, @RequestParam("updatedDifficulty")Difficulty difficulty,
                   @RequestParam("updatedKnowledge")String knowledge) throws SQLException {
        int num = question.hashCode();

        String sql = "INSERT INTO question (num, question, answer, type, difficulty, knowledge) VALUES (?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, num);
        pstmt.setString(2, question);
        pstmt.setString(3, answer);
        pstmt.setInt(4, type.ordinal());
        pstmt.setInt(5, difficulty.ordinal());
        pstmt.setString(6, knowledge);
        pstmt.executeUpdate();
        return num;
    }

    @RequestMapping("/remove")
    public void delete(@RequestParam("question")String question) throws SQLException {
        int num = question.hashCode();
        String sql = "DELETE FROM question WHERE num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, num);
        pstmt.executeUpdate();
    }

    @RequestMapping("/modify")
    public int modify(@RequestParam("originalQuestion")String oriQuestion, @RequestParam("updatedQuestion")String question, @RequestParam("updatedAnswer")String answer,
                      @RequestParam("updatedType")Type type, @RequestParam("updatedDifficulty")Difficulty difficulty,
                      @RequestParam("updatedKnowledge")String knowledge) throws SQLException {
        delete(oriQuestion);
        return add(question, answer, type, difficulty, knowledge);
    }
}
