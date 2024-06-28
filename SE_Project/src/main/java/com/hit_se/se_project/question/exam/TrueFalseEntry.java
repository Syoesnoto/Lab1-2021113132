package com.hit_se.se_project.question.exam;

import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.assistant.Type;

public class TrueFalseEntry extends ExamEntry {
    public TrueFalseEntry(Question question, int score) {
        super(question, score);
        this.type = Type.true_false;
    }
}
