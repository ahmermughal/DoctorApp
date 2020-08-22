package com.idevelopstudio.doctorapp.network;

import com.idevelopstudio.doctorapp.models.Token;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DoctorApi {

    @FormUrlEncoded
    @POST("doctor/signin")
    Observable<Token> doctorSignIn(
      @Field("d_id") String uid
    );

    @FormUrlEncoded
    @POST("doctor/signup")
    Observable<Token> doctorSignUp(
            @Field("d_id") String uid,
            @Field("email") String email,
            @Field("f_name") String firstName,
            @Field("l_name") String lastName,
            @Field("country") String country
    );



}
