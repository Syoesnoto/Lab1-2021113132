package com.hit_se.se_project.question.exam;

import com.hit_se.se_project.question.Question;
import com.hit_se.se_project.question.assistant.Type;

public class BlankFillingEntry extends ExamEntry {
    public BlankFillingEntry(Question question, int score) {
        super(question, score);
        this.type = Type.fill_in_blank;
    }
}
