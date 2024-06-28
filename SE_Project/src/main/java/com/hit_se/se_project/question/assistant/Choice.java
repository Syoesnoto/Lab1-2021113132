package com.hit_se.se_project.question.assistant;

public enum Choice {
    A, B, C, D;

    public static Choice convert(String s) {
        switch (s) {
            case "A":
                return A;
            case "B":
                return B;
            case "C":
                return C;
            case "D":
                return D;
            default:
                return null;
        }
    }
}
