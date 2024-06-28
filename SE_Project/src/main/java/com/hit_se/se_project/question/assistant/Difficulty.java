package com.hit_se.se_project.question.assistant;

public enum Difficulty {
    easy, medium, hard;

    public static Difficulty convert(int value) {
        switch (value) {
            case 0:
                return easy;
            case 1:
                return medium;
            case 2:
                return hard;
            default:
                return null;
        }
    }
}
