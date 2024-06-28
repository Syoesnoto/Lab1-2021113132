package com.hit_se.se_project.question.exam;

import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.assistant.Type;

public class ChoiceEntry extends ExamEntry {
    public ChoiceEntry(Question question, int score) {
        super(question, score);
        this.type = Type.choice;
    }
}
