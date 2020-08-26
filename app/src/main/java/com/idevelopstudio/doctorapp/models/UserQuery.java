package com.idevelopstudio.doctorapp.models;

import com.squareup.moshi.Json;

import java.util.Date;

public class UserQuery{

    @Json(name = "id")
    private int queryID;

    @Json(name = "question")
    private String questionTitle;

    @Json(name = "question_desc")
    private String questionDescription;

    @Json(name = "specialization_id")
    private int specializationID;

    @Json(name = "critical_status")
    private String criticalStatus;

    private String createdAt;


    public int getQueryID() {
        return queryID;
    }

    public void setQueryID(int queryID) {
        this.queryID = queryID;
    }



    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public int getSpecializationID() {
        return specializationID;
    }

    public void setSpecializationID(int specializationID) {
        this.specializationID = specializationID;
    }

    public String getCriticalStatus() {
        return criticalStatus;
    }

    public void setCriticalStatus(String criticalStatus) {
        this.criticalStatus = criticalStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public UserQuery(int queryID, String questionTitle, String questionDescription, int specializationID, String criticalStatus, String createdAt) {
        this.queryID = queryID;
        this.questionTitle = questionTitle;
        this.questionDescription = questionDescription;
        this.specializationID = specializationID;
        this.criticalStatus = criticalStatus;
        this.createdAt = createdAt;
    }
}
