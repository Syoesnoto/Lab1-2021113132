package com.hit_se.se_project.question;

import com.hit_se.se_project.question.assistant.Choice;
import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;

public class ChoiceQuestion extends Question {
    private Choice answer;

    public ChoiceQuestion(int num, String question, String answer, Type type, Difficulty difficulty, String knowledge) {
        super(num, question, answer, type, difficulty, knowledge);
        this.answer = Choice.convert(super.getAnswer());
    }
}
