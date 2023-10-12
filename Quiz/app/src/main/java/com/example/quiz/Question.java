package com.example.quiz;

public class Question {
    private String text;
    private boolean answer;
    private boolean answered;
    private boolean valid;

    public Question(String text, boolean answer) {
        this.text = text;
        this.answer = answer;
        answered = false;
        valid = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
