package com.example.project;

public class Answer {
    public final int id;
    String text;
    int value; // 0 SAU 1
    double points; //cat valoreaza raspunsul
    public Answer(String text, int value, double points){
        this.id = Quizz.answerID;
        Quizz.answerID++;
        this.value = value;
        this.text =text;
        this.points = points;
    }
}
