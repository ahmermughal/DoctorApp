package com.idevelopstudio.doctorapp.network;

import com.idevelopstudio.doctorapp.models.Token;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {


    @FormUrlEncoded
    @POST("auth/patient/signin")
    Observable<Token> userSignIn(
            @Field("p_id") String uid
    );

    @FormUrlEncoded
    @POST("auth/patient/signup")
    Observable<Token> userSignUp(
            @Field("p_id") String uid,
            @Field("email") String email,
            @Field("f_name") String firstName,
            @Field("l_name") String lastName,
            @Field("age") String age,
            @Field("weight") String weight,
            @Field("height") String height
    );

}
