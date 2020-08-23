package com.idevelopstudio.doctorapp.network;

import com.idevelopstudio.doctorapp.models.Token;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DoctorApi {

    @FormUrlEncoded
    @POST("auth/doctor/signin")
    Observable<Token> doctorSignIn(
      @Field("d_id") String uid
    );


    @Multipart
    @POST("auth/doctor/signup")
    Observable<Token> doctorSignUpPakistan(
            @Part MultipartBody.Part uidPart,
            @Part MultipartBody.Part emailPart,
            @Part MultipartBody.Part fNamePart,
            @Part MultipartBody.Part lNamePart,
            @Part MultipartBody.Part countryPart,
            @Part MultipartBody.Part pmdcPart,
            @Part MultipartBody.Part specializationsPart,
            @Part MultipartBody.Part uploadPart
    );

    @Multipart
    @POST("auth/doctor/signup")
    Observable<Token> doctorSignUpOther(
            @Part MultipartBody.Part uidPart,
            @Part MultipartBody.Part emailPart,
            @Part MultipartBody.Part fNamePart,
            @Part MultipartBody.Part lNamePart,
            @Part MultipartBody.Part countryPart,
            @Part MultipartBody.Part specializationsPart,
            @Part List<MultipartBody.Part> uploadsPart
    );





}
