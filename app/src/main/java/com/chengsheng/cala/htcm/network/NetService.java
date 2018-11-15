package com.chengsheng.cala.htcm.network;

import com.chengsheng.cala.htcm.model.datamodel.AppointmentDetail;
import com.chengsheng.cala.htcm.model.datamodel.ExamApponitments;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesList;
import com.chengsheng.cala.htcm.model.datamodel.LoginData;
import com.chengsheng.cala.htcm.model.datamodel.SMSVerificationResult;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface NetService {

    @FormUrlEncoded
    @POST("token")
    Observable<LoginData> login(@Field("client_id") String c_id,
                                @Field("grant_type") String grantType,
                                @Field("username") String userName,
                                @Field("password") String passWord,
                                @Field("client_secret") String clientSecret,
                                @Field("scope") String scope);


    @FormUrlEncoded
    @POST("sms-verification-codes")
    Observable<SMSVerificationResult> getSMSVerification(@Field("phone_number") String phoneNumber);

    @FormUrlEncoded
    @POST("account-registrations")
    Observable<ResponseBody> commitRegistrInfo(@Field("phone_number")String phoneNumber,
                                               @Field("password")String passWord,
                                               @Field("vcerification_code_id")String verificationCodeId,
                                               @Field("vcerification_code")String verificationCode,
                                               @Field("client_id")String clientId);

    @GET("app-exam-package")
    Observable<ExamApponitments> getComboInfo();//获取套餐信息。


    @GET
    Observable<AppointmentDetail> getCombonDetail(@Url String url,@Header("Authorization") String header);//获取套餐详情

    @GET
    Observable<ResponseBody> checkIsCollect(@Url String url,@Header("Authorization")String header);//检查

    @FormUrlEncoded
    @POST("api/physical-exam-item/collect")
    Observable<ResponseBody> collectCombo(@Field("id") String id,@Header("Authorization") String header);//收藏套餐


    @DELETE
    Observable<ResponseBody> cancelCollect(@Url String url,@Header("Authorization") String header);//取消收藏

    @GET("api/family/account-family-members")
    Observable<FamiliesList> getFamiliesList(@Header("Authorization")String header);//获取家人列表

    @GET
    Observable<FamiliesDetailInfo> getFamiliesDetail(@Url String url,@Header("Authorization") String header);//获取家人详细信息
}
