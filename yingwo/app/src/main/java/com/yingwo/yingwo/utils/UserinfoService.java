package com.yingwo.yingwo.utils;


import com.yingwo.yingwo.model.AcademyListModel;
import com.yingwo.yingwo.model.SchoolListModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangyu on 9/1/16.
 */

public interface UserinfoService {
    public static String BASE_URL = "http://yw.zhibaizhi.com/yingwophp/api/v1/";

    //注册 请求短信验证码接口
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Sms/Send")
    Call<String> requestVerify(@Field("mobile") String mobile);

    //注册 请求短信验证码接口
    @GET("getRegisterSMS")
    Call<String> requestVerify1(@Query("mobile") String mobile);

    //注册 验证短信接口
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Sms/Check")
    Call<String> verifySms(@Query("mode") String mode,@Query("mobile") String mobile,@Query("code") String code);

    //注册接口    @Headers("X-Requested-With:XMLHttpRequest")

    @Headers("X-Requested-With:XMLHttpRequest")
    @FormUrlEncoded
    @POST("User/Register")
    Observable<String> register(@Field("password") String password,@Field("mobile") String mobile);

    //登录接口
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/Login")
    Observable<String> login(@Field("mobile") String mobile,@Field("password") String password);

    //获取学校列表
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("school/school_list_by_group")
    Observable<SchoolListModel> getSchools();

    //获取专业列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("school/academy_list_by_group")
    Observable<AcademyListModel> getAcademyList(@Field("school_id") String school_id);

    //修改用户个人信息
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/Update")
    Call<String> updateUserInfo(@Query("name")String name,@Query("password")String passwd,@Query("face_img")String img_url,@Query("grade")String grade,@Query("signature")String signature,@Query("school_id")String school_id,@Query("academy_id")String academy_id);

    //完善个人信息
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/Base_info")
    Observable<String> updateBaseInfo(@Field("name") String name,@Field("grade")String grade,@Field("signature")String signature,@Field("sex")int sex,@Field("face_img")String img_url,@Field("school_id")String school_id,@Field("academy_id")String academy_id);

}
