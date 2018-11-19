package com.chengsheng.cala.htcm.network;

import com.chengsheng.cala.htcm.model.datamodel.AppointmentDetail;
import com.chengsheng.cala.htcm.model.datamodel.ExamApponitments;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesList;
import com.chengsheng.cala.htcm.model.datamodel.LoginData;
import com.chengsheng.cala.htcm.model.datamodel.Message;
import com.chengsheng.cala.htcm.model.datamodel.SMSVerificationResult;
import com.chengsheng.cala.htcm.model.datamodel.URLResult;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    @FormUrlEncoded
    @POST("api/family/account-family-members/sms-validation-code")
    Observable<Message> addFamiliesCodeRequest(@Header("Authorization") String header,@Field("mobile") String cellphone);

    @Multipart
    @POST("api/file-storage/files")
    Observable<URLResult> uploadFile(@Header("Authorization")String header, @PartMap Map<String,RequestBody> map, @Part MultipartBody.Part file);//上传文件


    @FormUrlEncoded
    @POST("api/family/account-family-members")
    Observable<ResponseBody> upLoadFamiliesInfo(@Header("Authorization") String header,@FieldMap Map<String,String> params);

    @Multipart
    @POST("api/family/account-family-members/{familiesId}/authentication")
    Observable<Message> authenticationFamilies(@Header("Authorization")String header,@Path("familiesId")String familiesID,@PartMap Map<String,RequestBody> map);//家人认证
}
