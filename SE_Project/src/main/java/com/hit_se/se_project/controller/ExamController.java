package com.hit_se.se_project.controller;

import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;
import com.hit_se.se_project.question.exam.Exam;
import com.hit_se.se_project.question.exam.ExamEntry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.util.Pair;
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
@RequestMapping("/exam")
public class ExamController extends Connector {
    QuestionController questionController;

    public ExamController() {
        questionController = new QuestionController();
    }

    @RequestMapping("/search")
    public void search(HttpServletResponse resp) throws SQLException, IOException {
        ResultSet rs = findExams();
        ArrayList<String> ls = new ArrayList<>();
        while(rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if(name.equals("question") || name.equals("user") || name.contains("_"))  continue;
            ls.add(name);
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

    @RequestMapping("/stuSearch")
    public void stuSearch(HttpServletResponse resp) throws SQLException, IOException {
        ResultSet rs = findExams();
        ArrayList<String> ls = new ArrayList<>();
        while(rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if(name.contains("_"))  ls.add(name);;
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

    @RequestMapping("/open")
    public void open(HttpServletResponse resp, @RequestParam("name")String name) throws SQLException, IOException {
        ResultSet rs = findExam(name);
        ArrayList<ExamEntry> ls = new ArrayList<>();
        while(rs.next()) {
            ExamEntry examEntry = createEntry(rs);
            ls.add(examEntry);
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

    @RequestMapping("/opening")
    public void opening(HttpServletResponse resp, @RequestParam("name")String name) throws SQLException, IOException {
        ResultSet rs = findExam(name);
        Exam exam = create(name, rs);
        /*将list集合装换成json对象*/
        JSONObject data = JSONObject.fromObject(exam);
        //接下来发送数据
        //  /*设置编码，防止出现乱码问题*/
        resp.setCharacterEncoding("utf-8");
        /*得到输出流*/
        PrintWriter respWriter = resp.getWriter();
        /*将JSON格式的对象toString()后发送*/
        respWriter.append(data.toString());
    }

    public int add(String name, Question question, String score) throws SQLException {
        String sql = "INSERT INTO " + name + " (question, type, answer, done, `right`, score, point) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, question.getNum());
        pstmt.setInt(2, question.getType().ordinal());
        pstmt.setString(3, null);
        pstmt.setBoolean(4, false);
        pstmt.setBoolean(5, false);
        int iscore = Integer.parseInt(score);
        pstmt.setInt(6, iscore);
        pstmt.setInt(7, 0);
        pstmt.executeUpdate();
        return question.getNum();
    }

    public int add(String name, ExamEntry examEntry) throws SQLException {
        String sql = "INSERT INTO " + name + " (question, type, answer, done, `right`, score, point) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, examEntry.getQuestion().getNum());
        pstmt.setInt(2, examEntry.getQuestion().getType().ordinal());
        pstmt.setString(3, examEntry.getAnswer());
        pstmt.setBoolean(4, examEntry.isDone());
        pstmt.setBoolean(5, examEntry.isRight());
        pstmt.setInt(6, examEntry.getScore());
        pstmt.setInt(7, examEntry.getPoint());
        pstmt.executeUpdate();
        return examEntry.getQuestion().getNum();
    }

    @RequestMapping("/remove")
    public void delete(@RequestParam("name")String name, @RequestParam("question")String question) throws SQLException {
        int num = question.hashCode();
        String sql = "DELETE FROM " + name + " WHERE question = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, num);
        pstmt.executeUpdate();
    }

    public int modify(String name, ExamEntry examEntry) throws SQLException {
        delete(name, examEntry.getQuestion().getQuestion());
        return add(name, examEntry);
    }

    public ExamEntry createEntry(ResultSet rs) throws SQLException {
        ResultSet rss = findQuestion(rs.getInt("question"));
        rss.next();
        Question question = questionController.create(rss);
        Type type = Type.convert(rs.getInt("type"));
        String answer = rs.getString("answer");
        boolean done = rs.getBoolean("done");
        boolean right = rs.getBoolean("right");
        int score = rs.getInt("score");
        int point = rs.getInt("point");
        return new ExamEntry(score, answer, question, right, type, done, point);
    }

    public Exam create(String name, ResultSet rs) throws SQLException {
        Exam exam = new Exam(name);
        while (rs.next()) {
            ExamEntry entry = createEntry(rs);
            exam.add(entry);
        }
        return exam;
    }

    public double[] statistic(Exam exam) {
        return exam.statistic();
    }

    public ArrayList<Pair<Integer, Boolean>> situation(Exam exam) {
        return exam.situation();
    }
}
