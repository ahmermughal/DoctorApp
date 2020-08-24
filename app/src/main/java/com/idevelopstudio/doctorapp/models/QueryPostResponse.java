package com.idevelopstudio.doctorapp.models;

public class QueryPostResponse {

    private String  error;
    private int id;
    private String question;
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
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

    public QueryPostResponse(String error, int id, String question, String message) {
        this.error = error;
        this.id = id;
        this.question = question;
        this.message = message;
    }
}
