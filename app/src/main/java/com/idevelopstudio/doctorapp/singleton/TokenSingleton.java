package com.idevelopstudio.doctorapp.singleton;

public class TokenSingleton {

    private String token;
    private static TokenSingleton mInstance;

    private TokenSingleton(){
        this.token = "111";
    }

    public static synchronized TokenSingleton getInstance(){
        if (mInstance == null){
            mInstance = new TokenSingleton();
        }
        return mInstance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
