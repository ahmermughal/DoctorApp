package com.idevelopstudio.doctorapp.network;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class NetworkManager {

    public static final String BASE_URL = "http://192.168.100.5:4000/api/";

    private static  NetworkManager mInstance;
    private Retrofit retrofit;

    private NetworkManager(){
        Moshi moshi = new Moshi.Builder().build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public static synchronized NetworkManager getInstance(){
        if(mInstance == null){
            mInstance = new NetworkManager();
        }
        return mInstance;
    }


    public DoctorApi getDoctorApi(){
        return retrofit.create(DoctorApi.class);
    }
    public UserApi getUserApi() {
        return retrofit.create(UserApi.class);
    }

}
