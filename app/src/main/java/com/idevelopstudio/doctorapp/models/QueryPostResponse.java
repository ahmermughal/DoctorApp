package com.idevelopstudio.doctorapp.models;

public class QueryPostResponse {

    private Boolean  error;
    private int id;
    private String question;
    private String message;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QueryPostResponse(Boolean error, int id, String question, String message) {
        this.error = error;
        this.id = id;
        this.question = question;
        this.message = message;
    }
}
