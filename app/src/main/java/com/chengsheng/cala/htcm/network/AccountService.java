package com.chengsheng.cala.htcm.network;

import com.chengsheng.cala.htcm.protocol.SMSVerificationResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface AccountService {


    @FormUrlEncoded
    @POST("sms-verification-codes")
    Observable<SMSVerificationResult> getSMSVerification(@Field("phone_number") String phoneNumber, @Field("type") String type);

    @FormUrlEncoded
    @PUT("sms-verification-codes")
    Observable<ResponseBody> smsVerification(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @PUT("user/account-passwords")
    Observable<ResponseBody> setAccountPasswd(@Field("new_password") String newPassword,
                                              @Field("old_password") String oldPasswd,
                                              @Field("phone_number_verification_code_id") String pnvci,
                                              @Field("vcerification_type") String vcerificationType,
                                              @Field("username") String username);
}
