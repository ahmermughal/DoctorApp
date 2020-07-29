package com.idevelopstudio.doctorapp.models;

public class DoctorAnswer {

    private String answer;
    private String doctorName;
    private String answerDate;

    public DoctorAnswer(String answer, String doctorName, String answerDate) {
        this.answer = answer;
        this.doctorName = doctorName;
        this.answerDate = answerDate;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }
}
