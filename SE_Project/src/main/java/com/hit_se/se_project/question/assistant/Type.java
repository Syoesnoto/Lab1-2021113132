package com.hit_se.se_project.question.assistant;

public enum Type {
    choice, true_false, fill_in_blank;

    public static Type convert(int value) {
        switch (value) {
            case 0:
                return choice;
            case 1:
                return true_false;
            case 2:
                return fill_in_blank;
            default:
                return null;
        }
    }
}
