package com.hit_se.se_project.question.exam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hit_se.se_project.question.assistant.Type;
import org.springframework.data.util.Pair;

import java.util.ArrayList;

public class Exam {
    private String name;
    private ArrayList<ExamEntry> choiceEntries;
    private ArrayList<ExamEntry> trueFalseEntries;
    private ArrayList<ExamEntry> blankFillEntries;

    public Exam(String name) {
        this.name = name;
        choiceEntries = new ArrayList<>();
        trueFalseEntries = new ArrayList<>();
        blankFillEntries = new ArrayList<>();
    }

    public Exam(JSONObject json) {
        this.choiceEntries = new ArrayList<>();
        this.trueFalseEntries = new ArrayList<>();
        this.blankFillEntries = new ArrayList<>();

        this.name = json.getString("name");
        JSONArray a = json.getJSONArray("choiceEntries");
        JSONArray b = json.getJSONArray("trueFalseEntries");
        JSONArray c = json.getJSONArray("blankFillEntries");

        for (int i = 0; i < a.size(); i++) {
            this.choiceEntries.add(new ExamEntry(a.getJSONObject(i)));
        }
        for (int i = 0; i < b.size(); i++) {
            this.trueFalseEntries.add(new ExamEntry(b.getJSONObject(i)));
        }
        for (int i = 0; i < c.size(); i++) {
            this.blankFillEntries.add(new ExamEntry(c.getJSONObject(i)));
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<ExamEntry> getChoiceEntries() {
        return choiceEntries;
    }

    public ArrayList<ExamEntry> getTrueFalseEntries() {
        return trueFalseEntries;
    }

    public ArrayList<ExamEntry> getBlankFillEntries() {
        return blankFillEntries;
    }

    public void add(ExamEntry examEntry) {
        switch (examEntry.getType()) {
            case choice:
                choiceEntries.add(examEntry);
            case true_false:
                trueFalseEntries.add(examEntry);
            case fill_in_blank:
                blankFillEntries.add(examEntry);
        }
    }

    public void delete(int num, Type type) {
        switch (type) {
            case choice:
                choiceEntries.remove(num);
            case true_false:
                choiceEntries.remove(num);
            case fill_in_blank:
                blankFillEntries.remove(num);
        }
    }

    public double[] statistic() {
        int choiceCount = choiceEntries.size();
        int trueFalseCount = trueFalseEntries.size();
        int blankFillCount = blankFillEntries.size();
        double all = choiceCount + trueFalseCount + blankFillCount;
        return new double[]{choiceCount / all, choiceCount / all, choiceCount / all};
    }

    public ArrayList<Pair<Integer, Boolean>> situation() {
        int i = 1;
        ArrayList<Pair<Integer, Boolean>> list = new ArrayList<>();
        for (ExamEntry examEntry : choiceEntries) {
            list.add(Pair.of(i, examEntry.isDone()));
            i++;
        }
        for (ExamEntry examEntry : trueFalseEntries) {
            list.add(Pair.of(i, examEntry.isDone()));
            i++;
        }
        for (ExamEntry examEntry : blankFillEntries) {
            list.add(Pair.of(i, examEntry.isDone()));
            i++;
        }
        return list;
    }
}
