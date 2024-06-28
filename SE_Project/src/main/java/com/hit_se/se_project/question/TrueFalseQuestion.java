package com.hit_se.se_project.question;

import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;

public class TrueFalseQuestion extends Question {
    private boolean answer;

    public TrueFalseQuestion(int num, String question, String answer, Type type, Difficulty difficulty, String knowledge) {
        super(num, question, answer, type, difficulty, knowledge);
        if (super.getAnswer().equals("true")) {
            this.answer = true;
        } else {
            this.answer = false;
        }
    }
}
