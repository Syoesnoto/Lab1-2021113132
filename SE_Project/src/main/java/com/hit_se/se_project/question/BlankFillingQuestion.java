package com.hit_se.se_project.question;

import com.hit_se.se_project.question.assistant.Difficulty;
import com.hit_se.se_project.question.assistant.Type;

public class BlankFillingQuestion extends Question {
    public BlankFillingQuestion(int num, String question, String answer, Type type, Difficulty difficulty, String knowledge) {
        super(num, question, answer, type, difficulty, knowledge);
    }
}
