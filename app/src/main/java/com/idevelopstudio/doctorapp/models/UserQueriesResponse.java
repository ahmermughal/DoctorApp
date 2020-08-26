package com.idevelopstudio.doctorapp.models;

import com.squareup.moshi.Json;

import java.util.List;

public class UserQueriesResponse {

    private int count;
    private int numberOfPages;

    public int getNumberOfPages() {
        int pages = 0;
        if (count > 0){
            if(count <= 50){
                pages = 1;
            }else{
                pages = count/50;
                if(count%50 != 0){
                    pages= pages+ 1;
                }
            }
        }
        return pages;
    }

    @Json(name = "questions")
    private List<UserQuery> userQueries;

    public UserQueriesResponse(List<UserQuery> userQueries) {
        this.userQueries = userQueries;
    }

    public List<UserQuery> getUserQueries() {
        return userQueries;
    }

    public void setUserQueries(List<UserQuery> userQueries) {
        this.userQueries = userQueries;
    }
}

