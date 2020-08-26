package com.idevelopstudio.doctorapp.network;

import com.idevelopstudio.doctorapp.models.QueryPostResponse;
import com.idevelopstudio.doctorapp.models.Token;
import com.idevelopstudio.doctorapp.models.UserQueriesResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
            @Field("height") String height,
            @Field("gender") String gender
    );


    @Multipart
    @POST("question/create")
    Observable<QueryPostResponse> userPostQuery(
            @Header("authentication") String token,
            @Part MultipartBody.Part uidPart,
            @Part MultipartBody.Part questionPart,
            @Part MultipartBody.Part questionDescPart,
            @Part MultipartBody.Part specializationIdPart,
            @Part MultipartBody.Part criticalStatusPart,
            @Part List<MultipartBody.Part> uploadsPart
    );

    @GET("patient/{uID}/questions")
    Observable<UserQueriesResponse> getUserQueries(
            @Header("authentication") String token,
            @Path("uID") String uid,
            @Query("page") int page
    );


}
