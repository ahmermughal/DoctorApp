package com.idevelopstudio.doctorapp.network;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;

public class NetworkManager {
    public static final String BASE_URL = "http://192.168.100.5:4000/graphql";

    private static NetworkManager mInstance;
    private ApolloClient apolloClient;

    private NetworkManager(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient).build();
    }

    public static synchronized NetworkManager getInstance(){
        if(mInstance == null){
            mInstance = new NetworkManager();
        }
        return mInstance;
    }

    public ApolloClient getApolloClient(){
        return apolloClient;
    }

}
