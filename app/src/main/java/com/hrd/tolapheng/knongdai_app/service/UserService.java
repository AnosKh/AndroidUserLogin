package com.hrd.tolapheng.knongdai_app.service;

import com.hrd.tolapheng.knongdai_app.model.User;
import com.hrd.tolapheng.knongdai_app.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by tolapheng on 12/27/16.
 */
public interface UserService {

    @POST("v1/authentication/mobile/login")
    Call<UserResponse> loginService(@Body User user);

    @POST("v1/authentication/mobile/register")
    Call<UserResponse> registerService(@Body User user);

    @POST("v1/authentication/mobile/update-user")
    Call<UserResponse> updateUserService(@Body User user);

    @POST("v1/authentication/login_with_scoial")
    Call<UserResponse> loginWithSocialService(@Body User user);

}
