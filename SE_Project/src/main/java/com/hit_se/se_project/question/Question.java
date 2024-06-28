package com.hit_se.se_project.question;

import com.alibaba.fastjson.JSONObject;
import com.hit_se.se_project.question.assistant.Type;
import com.hit_se.se_project.question.assistant.Difficulty;

public class Question {
    private int num;  //题目编号
    private String question;  //题目描述
    private String answer;  //题目的答案
    protected Type type;  //题型
    private Difficulty difficulty;  //题目难易度
    private String knowledge;  //题目知识点

    public Question(int num, String question, String answer, Type type, Difficulty difficulty, String knowledge) {
        this.num = num;
        this.question = question;
        this.answer = answer;
        this.type = type;
        this.difficulty = difficulty;
        this.knowledge = knowledge;
    }

    public Question(JSONObject json) {
        this.num = json.getIntValue("num");
        this.question = json.getString("question");
        this.answer = json.getString("answer");
        this.type = Type.valueOf(json.getString("type"));
        this.difficulty = Difficulty.valueOf(json.getString("difficulty"));
        this.knowledge = json.getString("knowledge");
    }

    public int getNum() {
        return num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer.toString();
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }
}