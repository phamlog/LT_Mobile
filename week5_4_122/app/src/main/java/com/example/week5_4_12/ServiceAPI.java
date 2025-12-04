package com.example.week5_4_12;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {

    @Multipart
    @POST("updateimages.php")
    Call<UpdateImageResponse> uploadImageProfile(
            @Part(Const.ID) RequestBody id,
            @Part MultipartBody.Part images
    );
}
