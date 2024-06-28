package com.hit_se.se_project.question.exam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;

public class ExamEntry {
    private Question question;  //试题的题目
    protected Type type;  //题型
    protected String answer;  //学生的回答
    private boolean done;  //是否作答
    private boolean right;  //作答对错情况
    private int score = 1;  //试题总分
    private int point;  //试题得分

    public ExamEntry(JSONObject json){
        this.question = new Question(json.getJSONObject("question"));
        this.type = Type.valueOf(json.getString("type"));
        this.answer = json.getString("answer");
        this.done = json.getBoolean("done");
        this.right = json.getBoolean("right");
        this.score = json.getInteger("score");
        this.point = json.getInteger("point");
    }

    public ExamEntry(Question question, int score) {
        this.question = question;
        this.answer = null;
        this.done = false;
        this.right = false;
        this.score = score;
        this.point = 0;
    }

    public ExamEntry(int score, String answer, Question question, boolean right, Type type, boolean done, int point) {
        this.question = question;
        this.type = type;
        this.answer = answer;
        this.done = done;
        this.right = right;
        this.score = score;
        this.point = point;
    }

    public Question getQuestion() {
        return question;
    }

    public Type getType() {
        return type;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isRight() {
        return right;
    }

    public int getScore() {
        return score;
    }

    public int getPoint() {
        return point;
    }

    public void judge(int point) {
        this.point = point;
        if (this.point == this.score) {
            this.right = true;
        } else {
            this.right = false;
        }
    }

    public void response(String answer) {
        this.answer = answer;
        done = true;
    }

    public boolean autoJudge() {
        if (done) {
            if (answer.equals(question.getAnswer())) {
                point = score;
                right = true;
            } else {
                point = 0;
                right = false;
            }
        }
        return right;
    }
}
